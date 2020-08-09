package algorithm;

import graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Processor {  // shallow copy

    private ScheduledTask _lastScheduled;
    private HashMap<String, ScheduledTask> _taskMap;  // shallow copy
    private int _id;

    public Processor(int id) {
        _lastScheduled = null;
        _taskMap = new HashMap<String, ScheduledTask>();
        _id = id;
    }

    /**
     * Copy constructor.
     * @param other The Processor object we are cloning.
     */
    public Processor(Processor other) {
        this(other._id);
        _taskMap = new HashMap<String, ScheduledTask>(other._taskMap);
        _lastScheduled = other._lastScheduled;
    }

    public ScheduledTask scheduleTask(Vertex task, int startTime) {
        _lastScheduled = new ScheduledTask(task, _id, startTime);
        _taskMap.put(task.getId(), _lastScheduled);
        return _lastScheduled;
    }

    public boolean isScheduled(String taskId) {
        return _taskMap.containsKey(taskId);
    }

    public ScheduledTask getLastScheduledTask() {
        return _lastScheduled;
    }

    public ScheduledTask getScheduledTask(String taskId) {
        return _taskMap.get(taskId);
    }

    public List<ScheduledTask> getScheduledTasks() {
        return new ArrayList<ScheduledTask>(_taskMap.values());
    }

    public int getId() {
        return _id;
    }

}
