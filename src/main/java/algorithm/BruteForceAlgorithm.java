package algorithm;

import graph.Graph;

import java.util.ArrayList;
import java.util.List;

public class BruteForceAlgorithm {

    private Graph _dependencyGraph;
    private int _numProcessors;

    public BruteForceAlgorithm(Graph dependencyGraph, int numProcessors){

        _dependencyGraph = dependencyGraph;
        _numProcessors = numProcessors;

    }

    public PartialSchedule findOptimalSchedule() {

        List<PartialSchedule> schedules = new ArrayList<PartialSchedule>();
        schedules.add(new PartialSchedule(_dependencyGraph, 8));

        int earliestFinishTime = Integer.MAX_VALUE;
        PartialSchedule bestSchedule = null;

        while (!schedules.isEmpty()) {
            PartialSchedule schedule = schedules.remove(0);
            if (schedule.isComplete() && schedule.getFinishTime() < earliestFinishTime) {
                bestSchedule = schedule;
                earliestFinishTime = schedule.getFinishTime();
            } else {
                schedules.addAll(schedule.extend());
            }
        }

        return bestSchedule;

    }


}
