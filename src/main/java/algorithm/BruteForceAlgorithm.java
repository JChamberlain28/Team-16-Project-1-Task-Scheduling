package algorithm;

import graph.Graph;

import java.util.ArrayList;
import java.util.List;
/*
This algorithm is used in unit testing to confirm the solution the DFS Branch and Bound produces is optimal
 */
public class BruteForceAlgorithm extends Algorithm {


    // calls super construct as this class extents the Algorithm abstract class
    public BruteForceAlgorithm(Graph dependencyGraph, int numProcessors){
        super(dependencyGraph, numProcessors);
    }

    public PartialSchedule findOptimalSchedule() {

        List<PartialSchedule> schedules = new ArrayList<PartialSchedule>();
        // add null schedule
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

        _finished = true;
        return bestSchedule;

    }


}
