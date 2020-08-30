package algorithm;

import graph.Graph;

public abstract class Algorithm {

    protected Graph _dependencyGraph;
    protected int _numProcessors;

    protected PartialSchedule _bestSchedule;  // stores best schedule found thus far, used by GUI
    protected boolean _finished; // flag used by GUI to know when the algorithm execution has finished
    protected int _numPartialSchedulesGenerated; // GUI stat
    protected int _numCompleteSchedulesGenerated; // GUI stat

    public Algorithm(Graph dependencyGraph, int numProcessors) {

        _dependencyGraph = dependencyGraph;
        _numProcessors = numProcessors;
        _bestSchedule = null;
        _finished = false;
        _numPartialSchedulesGenerated = 0;
        _numCompleteSchedulesGenerated = 0;

    }

    /**
     * Finds and returns the optimal schedule.
     * @return
     */
    public abstract PartialSchedule findOptimalSchedule();

    public PartialSchedule getBestSchedule() {
        return _bestSchedule;
    }

    public int getNumPartialSchedules() {
        return _numPartialSchedulesGenerated;
    }

    public int getNumCompleteSchedules() {
        return _numCompleteSchedulesGenerated;
    }

    public boolean isFinished() {
        return _finished;
    }

}
