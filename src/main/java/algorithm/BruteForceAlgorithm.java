package algorithm;

import graph.Graph;

import java.util.ArrayList;
import java.util.List;

public class BruteForceAlgorithm extends Algorithm {

    public BruteForceAlgorithm(Graph dependencyGraph, int numProcessors){
        super(dependencyGraph, numProcessors);
    }

    public PartialSchedule findOptimalSchedule() {

        List<PartialSchedule> schedules = new ArrayList<PartialSchedule>();
        schedules.add(new PartialSchedule(_dependencyGraph, _numProcessors));

        int earliestFinishTime = Integer.MAX_VALUE;
        PartialSchedule bestSchedule = null;

        while (!schedules.isEmpty()) {

            PartialSchedule schedule = schedules.remove(0);
            _numPartialSchedulesGenerated++;

            if (schedule.isComplete()) {

                _numCompleteSchedulesGenerated++;
                if (schedule.getFinishTime() < earliestFinishTime) {
                    bestSchedule = schedule;
                    earliestFinishTime = schedule.getFinishTime();
                }

            } else {
                schedules.addAll(schedule.extend(_dependencyGraph));
            }
        }

        return bestSchedule;

    }


}
