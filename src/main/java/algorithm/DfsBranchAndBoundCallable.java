package algorithm;


import graph.Graph;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;

public class DfsBranchAndBoundCallable implements Callable<Void> {

    private final ParallelisedDfsBranchAndBound _algo;
    private Graph _dependencyGraph;
    private int _numProcessors;
    private List<PartialSchedule> _stack;
    private int _threadId;

    public DfsBranchAndBoundCallable(ParallelisedDfsBranchAndBound algo, int numProcessors, List<PartialSchedule> initStack,
                             int threadId) {  // TODO: better way to close threads, experiment

        _algo = algo;
        _numProcessors = numProcessors;
        _dependencyGraph = algo._dependencyGraph;
        _stack = new ArrayList<PartialSchedule>(initStack);
        _threadId = threadId;

    }


    @Override
    public Void call() {

        LinkedList<PartialSchedule> cacheList = new LinkedList<PartialSchedule>();
        Set<PartialSchedule> cacheSet = new HashSet<PartialSchedule>();

        System.out.println("thread " + _threadId + " working");

        while (!_stack.isEmpty()) {

            //    Pop partial schedule off of stack and name curr_schedule
            PartialSchedule currentSchedule = _stack.remove(_stack.size()-1);

            _algo._numPartialSchedulesGenerated++;
            if (_algo.updateCache(currentSchedule)) {  // if this schedule does not exist in cache
                if (CostFunction.getHeuristicCost(currentSchedule, _dependencyGraph) < _algo.getEarliestFinishTime()) {
                    //System.out.println(currentSchedule.getToSchedule().size());
                    if (currentSchedule.isComplete()) {
                        _algo.setIfBestSchedule(currentSchedule);
                    } else {
                        _stack.addAll(currentSchedule.extend(_dependencyGraph));
                    }
                }
            }

        }

        System.out.println("Thread " + _threadId + " closing");
        System.out.flush();
        return null;

    }

}
