package algorithm;




import graph.Graph;
import graph.Vertex;

import java.awt.*;
import java.util.List;
import java.util.PriorityQueue;
import java.util.*;


public class AStarAlgorithm extends Algorithm {

    public AStarAlgorithm(Graph dependencyGraph, int numProcessors) {
        super(dependencyGraph, numProcessors);
    }

    @Override
    public PartialSchedule findOptimalSchedule() {

        HashSet<Integer> scheduleSet = new HashSet<Integer>();

        PriorityQueue<PartialSchedule> open = new PriorityQueue<PartialSchedule>(
                (a, b) -> Float.compare(a.getHeuristicCost(_dependencyGraph),
                        b.getHeuristicCost(_dependencyGraph))
        );

        PartialSchedule nullSchedule = new PartialSchedule(_dependencyGraph, _numProcessors);
        open.add(nullSchedule);

        while(!open.isEmpty()) {
            PartialSchedule p = open.poll();
            _numPartialSchedulesGenerated++;

            if (p.isComplete()) {
                _numCompleteSchedulesGenerated++;
                _bestSchedule = p;
                _finished = true;
                return _bestSchedule;
            }

            List<PartialSchedule> children = p.extend(_dependencyGraph);
            for (PartialSchedule child : children) {
                // PartialSchedule#hashCode handles duplicate detection for us
                if (scheduleSet.add(child.hashCode())) {
                    open.add(child);
                }
            }

        }

        _finished = true;
        return null;

    }

}