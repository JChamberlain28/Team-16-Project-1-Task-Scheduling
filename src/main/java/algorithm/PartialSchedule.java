package algorithm;

import graph.Graph;
import graph.Vertex;

import java.util.*;

public class PartialSchedule {

    private PartialSchedule _parent;
    private int[] _processorEndTimes;
    private int _processor;
    private int _startTime;
    private Vertex _task;

    // Used to prevent the creation of duplicate PartialSchedule objects, i.e. schedules where the tasks are ordered
    // in the same way at the same time but are simply scheduled on different processors.
    private List<String> _processorStrings;

    private HashSet<Vertex> _toSchedule;  // Protects against duplicate task scheduling due to multiple dependencies

    // Maps the label of a Vertex to the PartialSchedule object where it was initially scheduled. Used in extend() to
    // query the end times of the dependencies of a task being scheduled.
    private Map<String, PartialSchedule> _scheduleMap;

    /**
     * Creates an empty PartialSchedule that is, effectively, the root of the solution-space tree.
     * @param dependencyGraph Graph object representing the dependencies between tasks.
     * @param numProcessors Number of processors that the tasks are being scheduled on.
     */
    public PartialSchedule(Graph dependencyGraph, int numProcessors) {

        _parent = null;
        _processorEndTimes = new int[numProcessors];
        _processor = 0;
        _startTime = 0;
        _task = null;
        _processorStrings = new ArrayList<String>(numProcessors);
        for (int i = 0; i < numProcessors; i++) {
            _processorStrings.add("");  // Initialise empty processor strings
        }

        _toSchedule = new HashSet<Vertex>();
        _toSchedule.add(dependencyGraph.getRoot());

    }

    /**
     * Creates a PartialSchedule that extends a parent PartialSchedule. Only used within the PartialSchedule#extend
     * method.
     * @param parent Parent PartialSchedule that is being extended.
     * @param processor Processor that the new task is being scheduled on.
     * @param startTime Start time of the new task.
     * @param task New task that is being scheduled.
     */
    private PartialSchedule(PartialSchedule parent, int processor, int startTime, Vertex task,
                            List<String> processorStrings) {

        _parent = parent;
        _processorEndTimes = parent._processorEndTimes.clone();
        _processorEndTimes[processor-1] = startTime + task.getCost();
        _processor = processor;
        _startTime = startTime;
        _task = task;

        _toSchedule = new HashSet<Vertex>(parent._toSchedule);
        _toSchedule.remove(task);
        _toSchedule.addAll(task.getOutgoingVertices());

        _scheduleMap = new HashMap<String, PartialSchedule>(parent._scheduleMap);
        _scheduleMap.put(task.getId(), parent);

        _processorStrings = processorStrings;

    }

    public int getProcessorEndTime(int processor) {
        return _processorEndTimes[processor-1];
    }

    public List<PartialSchedule> extend(Graph dependencyGraph) {

        //TODO: implement duplicate schedule pruning

        List<PartialSchedule> partialSchedules = new ArrayList<PartialSchedule>();
        for (Vertex task : _toSchedule) {
            if (allDependenciesScheduled(task)) {

                // TODO: This is overall pretty inefficient (nested loops), and could do with some optimising in future.

                HashSet<HashSet<String>> processorStringsSet = new HashSet<HashSet<String>>();
                for (int i = 0; i < _processorEndTimes.length; i++) {

                    int pEarliestStartTime = _processorEndTimes[i];
                    for (Vertex dependency : task.getIncomingVertices()) {

                        PartialSchedule dSchedule = _scheduleMap.get(dependency.getId());
                        if (dSchedule._processor == i) {
                            // Do nothing as this start time would have to be <= processor end time
                        } else {
                            int tempStartTime = dSchedule._startTime + dependency.getCost() +
                                    dependencyGraph.getEdgeWeight(dependency.getId(), task.getId());
                            pEarliestStartTime = Math.max(pEarliestStartTime, tempStartTime);
                        }

                    }

                    // TODO: come up with more unique separator character ('?' *may* be used as a label for a vertex)
                    List<String> processorStrings = new ArrayList<String>(this._processorStrings);
                    processorStrings.set(i, processorStrings.get(i) + task.getId() + "?" + pEarliestStartTime);

                    // TODO: check that hash sets can be used to query a hash set
                    HashSet<String> processorStringSet = new HashSet<String>(processorStrings);

                    if (!processorStringsSet.contains(processorStringSet)) {
                        // No duplicate of this PartialSchedule exists
                        processorStringsSet.add(new HashSet<String>(processorStrings));
                        partialSchedules.add(new PartialSchedule(this, i, pEarliestStartTime, task,
                                processorStrings));
                    }

                }

            } else {
                // Task will get added again to _toSchedule once its dependency has been processed
                _toSchedule.remove(task);
            }
        }

        return partialSchedules;

    }

    private boolean allDependenciesScheduled(Vertex task) {

        for (Vertex dependency : task.getIncomingVertices()) {
            if (!_scheduleMap.containsKey(dependency.getId())) {
                return false;
            }
        }
        return true;

    }

}
