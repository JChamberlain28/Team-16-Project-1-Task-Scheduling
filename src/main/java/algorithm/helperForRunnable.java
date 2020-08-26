package algorithm;

import graph.Graph;
import java.util.List;
import  java.util.ArrayList;
import input.InputParser;

public class helperForRunnable implements Runnable{

    private List<PartialSchedule> _stack;
    private int _numProcessors;
    private List<Boolean> _busyList;
    private int _threadId;

    public helperForRunnable(List<PartialSchedule>stack, int numProcessors, List<Boolean> busyList, int threadId) {
        _stack = stack;
        _numProcessors = numProcessors;
        _threadId = threadId;
        _busyList = busyList;
    }


    @Override
    public void run() {
        System.out.println("thread working");

        boolean dontExit = true;


        while(dontExit) {
            while (!_stack.isEmpty()) {
                _busyList.set(_threadId, true);

                System.out.println("thread working in the loop");

                //    Pop partial schedule off of stack and name curr_schedule
                PartialSchedule currentSchedule = _stack.remove(_stack.size() - 1);
                //    Get cost of curr_schedule as curr_cost
                int currentFinishTime = currentSchedule.getFinishTime();
                //    if curr_cost < lowest_cost:
                if (currentFinishTime < ParallelisedDfsBranchAndBound.earliestFinishTime) {
                    //            if curr_schedule is a complete schedule:
                    if (currentSchedule.isComplete()) {
                        //    lowest_cost = curr_cost
                        ParallelisedDfsBranchAndBound.earliestFinishTime = currentFinishTime;
                        //            best_schedule = curr_schedule
                        ParallelisedDfsBranchAndBound.bestSchedule = currentSchedule;

                    }
                    //        else:
                    else {
                        //    Extend curr_schedule and add all children schedules to the stack
                        _stack.addAll(currentSchedule.extend());
                        //
                    }

                }

                _busyList.set(_threadId, false);

            }
            dontExit = _busyList.contains(true);
        }


    }
}
