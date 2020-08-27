package algorithm;

import graph.Graph;
import graph.Vertex;

import java.util.List;
import java.util.PriorityQueue;
import java.util.*;


public class AStarAlgorithm {

    private final Graph _dependencyGraph;
    private final int _numProcessors;

    public AStarAlgorithm(Graph dependencyGraph, int numProcessors){
        _dependencyGraph = dependencyGraph;
        _numProcessors = numProcessors;
    }

    public PartialSchedule findOptimalSchedule() {

        HashSet<Integer> scheduleSet = new HashSet<Integer>();

        PriorityQueue<PartialSchedule> open = new PriorityQueue<PartialSchedule>(
                (a, b) -> Float.compare(CostFunction.getHeuristicCost(a, _dependencyGraph),
                        CostFunction.getHeuristicCost(b, _dependencyGraph))
        );

        PartialSchedule nullSchedule = new PartialSchedule(_dependencyGraph, _numProcessors);
        open.add(nullSchedule);

        while(!open.isEmpty()) {
            PartialSchedule p = open.poll();

            if (p.isComplete()) {
                return p;
            }

            List<PartialSchedule> children = p.extend(_dependencyGraph);
            for (PartialSchedule child : children) {
                // PartialSchedule#hashCode handles duplicate detection for us
                if (scheduleSet.add(child.hashCode())) {
                    open.add(child);
                }
            }

        }

        return null;

    }

}