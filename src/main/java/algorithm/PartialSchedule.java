package algorithm;

import graph.Graph;
import graph.Vertex;

import java.util.*;
import java.util.stream.Collectors;

public class PartialSchedule {

    // Stores the current end times for each processor
    private final int[] _processorEndTimes;

    // Stores cumulative idle time across all processors
    private final int _idleTime;

    // Stores information regarding what task has been scheduled on top of the parent and how.
    private final ScheduledTask _scheduledTask;

    // Used to prevent the creation of duplicate PartialSchedule objects, i.e. schedules where the tasks are ordered
    // in the same way at the same time but are simply scheduled on different processors.
    private final List<String> _processorStrings;

    // TODO: storing an entire HashMap / HashSet seems inefficient - consider removing _parent field: see below

    // Protects against duplicate task scheduling due to multiple dependencies
    private final HashSet<Integer> _toSchedule;

    // Maps the label of a Vertex to its corresponding ScheduledTask object
    private final ScheduledTask[] _scheduledTasks;

    /**
     * Creates an empty PartialSchedule that is, effectively, the root of the solution-space tree.
     * @param dependencyGraph Graph object representing the dependencies between tasks.
     * @param numProcessors Number of processors that the tasks are being scheduled on.
     */
    public PartialSchedule(Graph dependencyGraph, int numProcessors) {

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

        _toSchedule = new HashSet<Integer>(dependencyGraph.getRoots());

        _scheduledTasks = new ScheduledTask[dependencyGraph.getVertices().size()];
        Arrays.fill(_scheduledTasks, null);

    }

    /**
     * Creates a PartialSchedule that extends a parent PartialSchedule. Only used within the PartialSchedule#extend
     * method.
     * @param parent Parent PartialSchedule that is being extended.
     * @param processor Processor that the new task is being scheduled on.
     * @param startTime Start time of the new task.
     * @param taskId The id of the task that is being scheduled.
     */
    private PartialSchedule(Graph dependencyGraph, PartialSchedule parent, int processor, int startTime, int taskId,
                            List<String> processorStrings) {

        Vertex task = dependencyGraph.getVertex(taskId);

        _processorEndTimes = parent._processorEndTimes.clone();
        _processorEndTimes[processor] = startTime + task.getCost();  // Update processor end time

        // Update cumulative idle time
        _idleTime = parent._idleTime + startTime - parent._processorEndTimes[processor];

        _scheduledTask = new ScheduledTask(processor, startTime, taskId);

        _scheduledTasks = parent._scheduledTasks.clone();
        _scheduledTasks[taskId] = _scheduledTask;

        _toSchedule = new HashSet<Integer>(parent._toSchedule);
        _toSchedule.remove(taskId);
        for (Vertex child : task.getOutgoingVertices()) {
            if (allDependenciesScheduled(child)) {  // only schedule task when all dependencies scheduled
                _toSchedule.add(child.getId());
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

    public int getTaskId() {
        return _scheduledTask.getTask();
    }

    public ScheduledTask getScheduledTask() {
        return _scheduledTask;
    }

    public ScheduledTask getScheduledTask(int id) {
        return _scheduledTasks[id];
    }

    public int getFinishTime() {
        return Arrays.stream(_processorEndTimes).max().getAsInt();
    }

    public List<ScheduledTask> getScheduledTasks() {
        return Arrays.stream(_scheduledTasks).filter(Objects::nonNull).collect(Collectors.toList());
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
    public List<PartialSchedule> extend(Graph dependencyGraph) {

        List<PartialSchedule> partialSchedules = new ArrayList<PartialSchedule>();
        for (int taskId : _toSchedule) {

            // TODO: This is overall pretty inefficient (nested loops), and could do with some optimising in future.
            Vertex task = dependencyGraph.getVertex(taskId);
            HashSet<HashSet<String>> processorStringsSet = new HashSet<HashSet<String>>();
            for (int i = 0; i < _processorEndTimes.length; i++) {  // Loop through each processor

                int pEarliestStartTime = _processorEndTimes[i];
                for (Vertex dependency : task.getIncomingVertices()) {

                    ScheduledTask scheduledTask = _scheduledTasks[dependency.getId()];
                    if (scheduledTask.getProcessor() == i) {
                        // Do nothing as this start time would have to be <= processor end time
                    } else {
                        int tempStartTime = scheduledTask.getStartTime() + dependency.getCost() +
                                dependencyGraph.getEdgeWeight(dependency.getId(), task.getId());
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
                    partialSchedules.add(new PartialSchedule(dependencyGraph, this, i, pEarliestStartTime, taskId,
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
            if (_scheduledTasks[dependency.getId()] == null) {
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
     * it return a set of IDs corresponding to the vertices that are eligible to be scheduled next.
     * @return
     */
    public Set<Integer> getToSchedule(){
        return _toSchedule;
    }

}
