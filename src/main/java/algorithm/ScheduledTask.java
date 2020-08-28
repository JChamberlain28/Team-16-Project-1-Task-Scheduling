package algorithm;

import graph.Graph;
import graph.Vertex;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ScheduledTask {

    private int _processor;
    private int _startTime;
    private int _taskId;

    public ScheduledTask(int processor, int startTime, int taskId) {
        _processor = processor;
        _startTime = startTime;
        _taskId = taskId;
    }

    public int getProcessor() {
        return _processor;
    }

    public int getStartTime() {
        return _startTime;
    }

    public int getTask() {
        return _taskId;
    }

    public void updateVertex(Graph dependencyGraph){
        Vertex task = dependencyGraph.getVertex(_taskId);
        task.setProcessorNumber(_processor + 1);
        task.setStartTime(_startTime);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(37, 101)
                .append(_startTime)
                .append(_taskId)
                .toHashCode();
    }

}
