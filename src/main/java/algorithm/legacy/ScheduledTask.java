package algorithm.legacy;

import graph.Vertex;

public class ScheduledTask {

    private Vertex _task;
    private int _processor;
    private int _startTime;

    public ScheduledTask(Vertex task, int processor, int startTime) {
        _task = task;
        _processor = processor;
        _startTime = startTime;
    }

    public Vertex getTask() {
        return _task;
    }

    public int getProcessor() {
        return _processor;
    }

    public int getStartTime() {
        return _startTime;
    }

}
