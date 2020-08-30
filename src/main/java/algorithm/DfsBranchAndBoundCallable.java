package algorithm;


import graph.Graph;

import java.util.*;
import java.util.concurrent.Callable;

public class DfsBranchAndBoundCallable implements Callable<Void> {

    private final DfsBranchAndBound _algo;
    private Graph _dependencyGraph;
    private int _numProcessors;
    private List<PartialSchedule> _stack;
    private int _threadId;

    public DfsBranchAndBoundCallable(DfsBranchAndBound algo, int numProcessors, List<PartialSchedule> initStack,
                                     int threadId) {

        _algo = algo;
        _numProcessors = numProcessors;
        _dependencyGraph = algo._dependencyGraph;
        _stack = new ArrayList<PartialSchedule>(initStack);
        _threadId = threadId;

    }


    @Override
    public Void call() {

        while (!_stack.isEmpty()) {

            //    Pop partial schedule off of stack and name curr_schedule
            PartialSchedule currentSchedule = _stack.remove(_stack.size()-1);

            _algo._numPartialSchedulesGenerated++;
            if (_algo.updateCache(currentSchedule)) {  // if this schedule does not exist in cache

                if (currentSchedule.getHeuristicCost(_dependencyGraph) < _algo.getEarliestFinishTime()) {
                    if (currentSchedule.isComplete()) {
                        _algo.setIfBestSchedule(currentSchedule);
                    } else {
                        List<PartialSchedule> children = new ArrayList<PartialSchedule>(currentSchedule.extend(_dependencyGraph));
                        children.sort((c1, c2) -> Float.compare(c2.getHeuristicCost(_dependencyGraph), c1.getHeuristicCost((_dependencyGraph))));
                        _stack.addAll(children);
                    }
                }

            }

        }

        return null;

    }

}
