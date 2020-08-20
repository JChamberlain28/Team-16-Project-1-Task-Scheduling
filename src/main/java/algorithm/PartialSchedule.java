package algorithm;

import graph.Graph;
import graph.Vertex;

import java.util.*;

public class PartialSchedule {

    // The dependency graph that this schedule adheres to
    private final Graph _dependencyGraph;

    // Stores the current end times for each processor
    private int[] _processorEndTimes;

    // Stores cumulative idle time across all processors
    private int _idleTime;

    // Stores information regarding what task has been scheduled on top of the parent and how.
    private ScheduledTask _scheduledTask;

    // Used to prevent the creation of duplicate PartialSchedule objects, i.e. schedules where the tasks are ordered
    // in the same way at the same time but are simply scheduled on different processors.
    private List<String> _processorStrings;

    // TODO: storing an entire HashMap / HashSet seems inefficient - consider removing _parent field: see below

    // Protects against duplicate task scheduling due to multiple dependencies
    private HashSet<Vertex> _toSchedule;

//    // Maps the label of a Vertex to the PartialSchedule object where it was initially scheduled. Used in extend() to
//    // query the end times of the dependencies of a task being scheduled.
//    private Map<String, PartialSchedule> _scheduleMap;

    // Maps the label of a Vertex to its corresponding ScheduledTask object
    private Map<String, ScheduledTask> _scheduledTaskMap;

    /**
     * Creates an empty PartialSchedule that is, effectively, the root of the solution-space tree.
     * @param dependencyGraph Graph object representing the dependencies between tasks.
     * @param numProcessors Number of processors that the tasks are being scheduled on.
     */
    public PartialSchedule(Graph dependencyGraph, int numProcessors) {

        _dependencyGraph = dependencyGraph;

        _processorEndTimes = new int[numProcessors];
        for (int i = 0; i < numProcessors; i++) {
            _processorEndTimes[i] = 0;
        }
        _idleTime = 0;

        _scheduledTask = null;
        _processorStrings = new ArrayList<String>(numProcessors);
        for (int i = 0; i < numProcessors; i++) {
            _processorStrings.add("");  // Initialise empty processor strings
        }

        _toSchedule = new HashSet<Vertex>();
        _toSchedule.addAll(dependencyGraph.getRoots());

        _scheduledTaskMap = new HashMap<String, ScheduledTask>();

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

        _dependencyGraph = parent._dependencyGraph;

        _processorEndTimes = parent._processorEndTimes.clone();
        _processorEndTimes[processor] = startTime + task.getCost();  // Update processor end time

        // Update cumulative idle time
        _idleTime = parent._idleTime + startTime - parent._processorEndTimes[processor];

        _scheduledTask = new ScheduledTask(processor, startTime, task);

        _scheduledTaskMap = new HashMap<String, ScheduledTask>(parent._scheduledTaskMap);
        _scheduledTaskMap.put(task.getId(), _scheduledTask);

        _toSchedule = new HashSet<Vertex>(parent._toSchedule);
        _toSchedule.remove(task);
        for (Vertex child : task.getOutgoingVertices()) {
            if (allDependenciesScheduled(child)) {  // only schedule task when all dependencies scheduled
                _toSchedule.add(child);
            }
        }

        _processorStrings = processorStrings;

    }

    /**
     * Checks whether there are any remaining tasks to schedule to determine if the PartialSchedule is complete (no
     * tasks left to schedule).
     * @return Whether or not this PartialSchedule is complete.
     */
    public boolean isComplete() {
        return _toSchedule.size() == 0;
    }

    public int[] getProcessorEndTimes() {
        return _processorEndTimes;
    }

    public int getProcessor() {
        return _scheduledTask.getProcessor();
    }

    public int getStartTime() {
        return _scheduledTask.getStartTime();
    }

    public Vertex getTask() {
        return _scheduledTask.getTask();
    }

    public ScheduledTask getScheduledTask() {
        return _scheduledTask;
    }

    public ScheduledTask getScheduledTask(String id) {
        return _scheduledTaskMap.get(id);
    }

    public int getFinishTime() {
        return Arrays.stream(_processorEndTimes).max().getAsInt();
    }

    public List<ScheduledTask> getScheduledTasks() {
        return new ArrayList<ScheduledTask>(_scheduledTaskMap.values());
    }

    /**
     * Returns the cumulative idle time overall all processors, i.e. the amount of time where a given processor is idle.
     * @return
     */
    public int getIdleTime() {
        return _idleTime;
    }

    /**
     * Extends this PartialSchedule by creating all of its possible children PartialSchedules. This is done by iterating
     * through the topological order of the dependency graph, where one 'layer' is processed per call of this method.
     * @return The list of newly created PartialSchedules.
     */
    public List<PartialSchedule> extend() {

        List<PartialSchedule> partialSchedules = new ArrayList<PartialSchedule>();
        for (Vertex task : _toSchedule) {

            // TODO: This is overall pretty inefficient (nested loops), and could do with some optimising in future.

            HashSet<HashSet<String>> processorStringsSet = new HashSet<HashSet<String>>();
            for (int i = 0; i < _processorEndTimes.length; i++) {  // Loop through each processor

                int pEarliestStartTime = _processorEndTimes[i];
                for (Vertex dependency : task.getIncomingVertices()) {

                    ScheduledTask scheduledTask = _scheduledTaskMap.get(dependency.getId());
                    if (scheduledTask.getProcessor() == i) {
                        // Do nothing as this start time would have to be <= processor end time
                    } else {
                        int tempStartTime = scheduledTask.getStartTime() + dependency.getCost() +
                                _dependencyGraph.getEdgeWeight(dependency.getId(), task.getId());
                        pEarliestStartTime = Math.max(pEarliestStartTime, tempStartTime);
                    }

                }

                // TODO: come up with more unique separator character ('?' *may* be used as a label for a vertex)
                List<String> processorStrings = new ArrayList<String>(this._processorStrings);
                processorStrings.set(i, processorStrings.get(i) + task.getId() + "?" + pEarliestStartTime);

                HashSet<String> processorStringSet = new HashSet<String>(processorStrings);

                if (!processorStringsSet.contains(processorStringSet)) {
                    // No duplicate of this PartialSchedule exists
                    processorStringsSet.add(new HashSet<String>(processorStrings));
                    partialSchedules.add(new PartialSchedule(this, i, pEarliestStartTime, task,
                            processorStrings));
                }

            }

        }

        return partialSchedules;

    }

    /**
     * Checks whether all of the dependencies of the passed Vertex (task) have already been scheduled.
     * @param task Task for which we are checking whether the dependencies have been scheduled.
     * @return Whether or not all dependencies have been scheduled.
     */
    private boolean allDependenciesScheduled(Vertex task) {

        for (Vertex dependency : task.getIncomingVertices()) {
            if (!_scheduledTaskMap.containsKey(dependency.getId())) {
                return false;
            }
        }
        return true;

    }

    /**
     * Returns whether or not two PartialSchedule objects are equivalent. Equivalent in this context means that each
     * PartialSchedule has the same task schedulings irrespective of the processor numbers.
     * @param other The PartialSchedule to compare against.
     * @return Whether or not the passed PartialSchedule is equivalent.
     */
    public boolean isEquivalent(PartialSchedule other) {
        HashSet<String> pStringSet = new HashSet<String>(this._processorStrings);
        pStringSet.removeAll(new HashSet<String>(other._processorStrings));
        return pStringSet.isEmpty();
    }

    /**
     * Returns a set of String objects to represent the task schedulings irrespective of processor.
     */
    public Set<String> getProcessorStringSet() {
        return new HashSet<String>(_processorStrings);
    }


    /**
     * it return a set of vertices that are eligible to be scheduled next.
     * @return
     */
    public Set<Vertex> getToSchedule(){
        return _toSchedule;
    }

}
