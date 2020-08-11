package algorithm.legacy;

import graph.Vertex;

import java.util.HashMap;

public class Schedule {

    private HashMap<Integer, Processor> _processorMap;
    private int _currentEndTime;

    public Schedule(int numProcessors) {
        _processorMap = new HashMap<Integer, Processor>();
        for (int i = 1; i < numProcessors+1; i++) {
            _processorMap.put(i, new Processor(i));
        }
        _currentEndTime = 0;
    }

    /**
     * Copy constructor.
     * @param other The Schedule object we are cloning.
     */
    public Schedule(Schedule other) {
        this(0);  // Just to initialise the processor map and current end time
        for (int id : other._processorMap.keySet()) {
            _processorMap.put(id, new Processor(other._processorMap.get(id)));
        }
        _currentEndTime = other._currentEndTime;
    }

    public ScheduledTask scheduleTask(int processor, int startTime, Vertex task) {
        Processor p = _processorMap.get(processor);
        ScheduledTask st = p.scheduleTask(task, startTime);
        _currentEndTime = Math.max(_currentEndTime, st.getStartTime() + st.getTask().getCost());
        return st;
    }

    // TODO: add intermediary interface to facilitate interaction with contained processors

}
