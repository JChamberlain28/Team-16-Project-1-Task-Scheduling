package algorithm;

import graph.Graph;

import java.util.*;

public class DfsBranchAndBoundBak extends Algorithm {

    private HashSet<Integer> _seenCacheSet;
    private LinkedList<Integer> _seenCacheList;
    private int _seenCacheCapacity;
    private int _seenCacheSize;

    public DfsBranchAndBoundBak(Graph dependencyGraph, int numProcessors){

        super(dependencyGraph, numProcessors);
        _seenCacheSet = new HashSet<Integer>();
        _seenCacheList = new LinkedList<Integer>();
        _seenCacheCapacity = 100000;
        _seenCacheSize = 0;

    }

    public PartialSchedule findOptimalSchedule(){
        System.out.println("Sequential");

        //    Initialise stack to store partial schedules to explore
        List<PartialSchedule> stack = new ArrayList<PartialSchedule>();

        stack.add(new PartialSchedule(_dependencyGraph, _numProcessors));

        int earliestFinishTime = Integer.MAX_VALUE;

        while (!stack.isEmpty()) {

            PartialSchedule currentSchedule = stack.remove(stack.size()-1);
            _numPartialSchedulesGenerated++;

            int currentFinishTime = currentSchedule.getFinishTime();

            if (currentSchedule.isComplete()) {

                _numCompleteSchedulesGenerated++;
                if (currentFinishTime < earliestFinishTime) {
                    earliestFinishTime = currentFinishTime;
                    _bestSchedule = currentSchedule;
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

                    if (currentSchedule.getHeuristicCost(_dependencyGraph) < earliestFinishTime) {
                        List<PartialSchedule> children = new ArrayList<PartialSchedule>(currentSchedule.extend(_dependencyGraph));
                        children.sort((c1, c2) -> Float.compare(c2.getHeuristicCost(_dependencyGraph), c1.getHeuristicCost((_dependencyGraph))));
                        stack.addAll(children);
                    }

                }
            }

        }

        _finished = true;
        return _bestSchedule;
    }

}
