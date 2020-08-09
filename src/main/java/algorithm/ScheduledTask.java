package algorithm;

import graph.Vertex;

public class ScheduledTask {

    private String _taskId;
    private int _processor;
    private int _startTime;

    public ScheduledTask(String taskId, int processor, int startTime) {
        _taskId = taskId;
        _processor = processor;
        _startTime = startTime;
    }

    public String getTaskId() {
        return _taskId;
    }

    public int getProcessor() {
        return _processor;
    }

    public int getStartTime() {
        return _startTime;
    }

}
