package algorithm;

import graph.Graph;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class  ParallelisedDfsBranchAndBound implements Runnable {

    private Graph _dependencyGraph;
    private int _numProcessors;
    private int _threads;
    List<Boolean> _busy = new ArrayList<Boolean>();

    public  ParallelisedDfsBranchAndBound(Graph dependencyGraph, int numProcessors, int threads){

        _dependencyGraph = dependencyGraph;
        _numProcessors = numProcessors;
        _threads = threads;
        for(int i=0;i<threads;i++){
            _busy.add(false);

        }
    }






    private List<PartialSchedule> stack = new ArrayList<PartialSchedule>();

    static public PartialSchedule bestSchedule = null;

    static public int earliestFinishTime = Integer.MAX_VALUE;

    public PartialSchedule findOptimalSchedule(){


        //    Initialise stack to store partial schedules to explore
//        List<PartialSchedule> stack = new ArrayList<PartialSchedule>();

        //    Add null solution to stack
        stack.add(new PartialSchedule(_dependencyGraph, _numProcessors));
        ExecutorService service = Executors.newFixedThreadPool(_threads);
        for(int i=0; i<_threads;i++){
            service.execute(new helperForRunnable(stack, _numProcessors,_busy,i));


        }

        //
        //    Set lowest_cost = Infinity
//        int earliestFinishTime = Integer.MAX_VALUE;
        //    Set best_schedule = null
//        PartialSchedule bestSchedule = null;
        //
        //while stack is not empty:
//        while (!stack.isEmpty()){
//            //    Pop partial schedule off of stack and name curr_schedule
//            PartialSchedule currentSchedule = stack.remove(stack.size()-1);
//            //    Get cost of curr_schedule as curr_cost
//            int currentFinishTime = currentSchedule.getFinishTime();
//            //    if curr_cost < lowest_cost:
//            if (currentFinishTime < earliestFinishTime){
//                //            if curr_schedule is a complete schedule:
//                if (currentSchedule.isComplete()){
//                    //    lowest_cost = curr_cost
//                    earliestFinishTime = currentFinishTime;
//                    //            best_schedule = curr_schedule
//                    bestSchedule = currentSchedule;
//
//                }
//                //        else:
//                else {
//                    //    Extend curr_schedule and add all children schedules to the stack
//                    stack.addAll(currentSchedule.extend());
//                    //
//                }
//
//            }
//
//        }

        //return best_schedule
        return bestSchedule;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

    }
}

