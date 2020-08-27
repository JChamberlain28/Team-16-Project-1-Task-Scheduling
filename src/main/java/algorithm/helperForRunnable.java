package algorithm;


import graph.Graph;
import javafx.concurrent.Task;

import java.sql.ClientInfoStatus;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class helperForRunnable implements Runnable {

    private final ParallelisedDfsBranchAndBound _algo;
    private Graph _graph;
    private List<PartialSchedule> _stack;
    //private LinkedBlockingDeque _stack;
    private int _numProcessors;
    private List<Boolean> _busyList;
    private int _threadId;

    public helperForRunnable(List<PartialSchedule> stack, int numProcessors, List<Boolean> busyList,
                             int threadId, Graph graph, ParallelisedDfsBranchAndBound algo) { //TODO: better way to close threads, experament
        _stack = stack;
        _numProcessors = numProcessors;
        _threadId = threadId;
        _busyList = busyList;
        _graph = graph;
        _algo = algo;
    }


    @Override
    public void run() {
        System.out.println("thread " + _threadId + " working");

        boolean dontExit = true;


        while(dontExit) {
            while (!_stack.isEmpty()) {
                _busyList.set(_threadId, true);

                //System.out.println("thread working in the loop");

                //    Pop partial schedule off of stack and name curr_schedule
                PartialSchedule currentSchedule = _algo.popOffStack();
                if (currentSchedule == null){
                    _busyList.set(_threadId, false);
                    break;
                }
                //    Get cost of curr_schedule as curr_cost
                int currentFinishTime = currentSchedule.getFinishTime();
                //    if curr_cost < lowest_cost:

                if (currentFinishTime < ParallelisedDfsBranchAndBound._earliestFinishTime) {
                    //            if curr_schedule is a complete schedule:
                    if (currentSchedule.isComplete()) {

                        //            best_schedule = curr_schedule
                        _algo.setIfBestSchedule(currentSchedule, currentFinishTime);
                    }
                    //        else:
                    else {
                        //    Extend curr_schedule and add all children schedules to the stack
                        _algo.addAllToStack(currentSchedule.extend(_graph));

                        //
                    }

                }

                _busyList.set(_threadId, false);

            }
            dontExit = _busyList.contains(true);
            //System.out.println(_threadId + ": dontExit = " + dontExit);
        }
        System.out.println("Thread " + _threadId + " closing");


    }


}
