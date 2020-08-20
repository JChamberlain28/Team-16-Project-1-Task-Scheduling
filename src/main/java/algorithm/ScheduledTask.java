package algorithm;

import graph.Vertex;

public class ScheduledTask {

    private int _processor;
    private int _startTime;
    private Vertex _task;

    public ScheduledTask(int processor, int startTime, Vertex task) {
        _processor = processor;
        _startTime = startTime;
        _task = task;
    }

    public int getProcessor() {
        return _processor;
    }

    public int getStartTime() {
        return _startTime;
    }

    public Vertex getTask() {
        return _task;
    }

    public void applyScheduleVals(){
        _task.setProcessorNumber(_processor);
        _task.setStartTime(_startTime);
    }

}
