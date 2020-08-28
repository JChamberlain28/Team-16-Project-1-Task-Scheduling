package algorithm;

import graph.Graph;

import java.util.*;

public class DfsBranchAndBound extends Algorithm {

    private HashSet<Integer> _seenCacheSet;
    private LinkedList<Integer> _seenCacheList;
    private int _seenCacheCapacity;
    private int _seenCacheSize;

    public DfsBranchAndBound(Graph dependencyGraph, int numProcessors){

        super(dependencyGraph, numProcessors);
        _seenCacheSet = new HashSet<Integer>();
        _seenCacheList = new LinkedList<Integer>();
        _seenCacheCapacity = 100000;
        _seenCacheSize = 0;

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

        int count = 0;

        //while stack is not empty:
        while (!stack.isEmpty()) {
            //    Pop partial schedule off of stack and name curr_schedule
            PartialSchedule currentSchedule = stack.remove(stack.size()-1);
            _numPartialSchedulesGenerated++;
            //    Get cost of curr_schedule as curr_cost
            int currentFinishTime = currentSchedule.getFinishTime();
            //    if curr_cost < lowest_cost:
            if (currentSchedule.isComplete()) {

                _numCompleteSchedulesGenerated++;
                if (currentFinishTime < earliestFinishTime) {
                    //  lowest_cost = curr_cost
                    earliestFinishTime = currentFinishTime;
                    //  best_schedule = curr_schedule
                    bestSchedule = currentSchedule;
                }

            } else {
                int hashCode = currentSchedule.hashCode();
                if (_seenCacheSet.add(hashCode)) {

                    _seenCacheList.addLast(hashCode);
                    _seenCacheSize++;
                    if (_seenCacheSize > _seenCacheCapacity) {
                        _seenCacheSet.remove(_seenCacheList.pollFirst());
                        _seenCacheSize--;
                    }

                    if (CostFunction.getHeuristicCost(currentSchedule, _dependencyGraph) < earliestFinishTime) {
                        stack.addAll(currentSchedule.extend(_dependencyGraph));
                    }

                }
            }

        }

        return bestSchedule;
    }

}
