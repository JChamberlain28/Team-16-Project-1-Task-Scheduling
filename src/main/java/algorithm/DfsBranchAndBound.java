package algorithm;

import graph.Graph;

import java.util.ArrayList;
import java.util.List;

public class DfsBranchAndBound {

    private Graph _dependencyGraph;
    private int _numProcessors;

    public DfsBranchAndBound(Graph dependencyGraph, int numProcessors){

        _dependencyGraph = dependencyGraph;
        _numProcessors = numProcessors;
    }

    public PartialSchedule findOptimalSchedule(){
        //    Initialise stack to store partial schedules to explore
        List<PartialSchedule> stack = new ArrayList<PartialSchedule>();

        //    Add null solution to stack
        stack.add(new PartialSchedule(_dependencyGraph, _numProcessors));
        //
        //    Set lowest_cost = Infinity
        int earliestFinishTime = Integer.MAX_VALUE;
        //    Set best_schedule = null
        PartialSchedule bestSchedule = null;
        //
        //while stack is not empty:
        while (!stack.isEmpty()){
            //    Pop partial schedule off of stack and name curr_schedule
            PartialSchedule currentSchedule = stack.remove(stack.size()-1);
            //    Get cost of curr_schedule as curr_cost
            int currentFinishTime = currentSchedule.getFinishTime();
            //    if curr_cost < lowest_cost:
            if (currentFinishTime < earliestFinishTime){
                //            if curr_schedule is a complete schedule:
                if (currentSchedule.isComplete()){
                    //    lowest_cost = curr_cost
                    earliestFinishTime = currentFinishTime;
                    //            best_schedule = curr_schedule
                    bestSchedule = currentSchedule;

                }
                //        else:
                else {
                    //    Extend curr_schedule and add all children schedules to the stack
                    stack.addAll(currentSchedule.extend(_dependencyGraph));
                    //
                }

            }

        }

        //return best_schedule
        return bestSchedule;
    }

}
