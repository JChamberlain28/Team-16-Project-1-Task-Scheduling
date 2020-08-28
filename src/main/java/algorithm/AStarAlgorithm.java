package algorithm;




import graph.Graph;
import graph.Vertex;

import java.awt.*;
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
                (a, b) -> Float.compare(CostFunction.getHeuristicCost(a, _dependencyGraph) + a.getFinishTime(),
                        CostFunction.getHeuristicCost(b, _dependencyGraph) + b.getFinishTime())
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

//    private float getHeuristicCost(PartialSchedule p) {
//
//        if (p.getScheduledTask() == null){
//            return 0.0f;
//        }
//
//        // Calculate idle time heuristic
//        float idleTimeHeuristic = (float) p.getIdleTime();
//        for (Vertex v : _dependencyGraph.getVertices()) {
//            idleTimeHeuristic += v.getCost();
//        }
//        idleTimeHeuristic /= p.getProcessorEndTimes().length;
//
//        // Calculate bottom level heuristic
//        float bottomLevelHeuristic = 0;
//        for (ScheduledTask st : p.getScheduledTasks()){
//            bottomLevelHeuristic = Math.max(bottomLevelHeuristic, st.getStartTime() +
//                    _dependencyGraph.getBottomLevel(st.getTask()));
//        }
//
//        // Calculate Data Ready Time heuristic
//        float drtHeuristic = 0;
//        for (int childId : p.getToSchedule()) {
//
//            Vertex child = _dependencyGraph.getVertex(childId);
//            float minDataReadyTime = Integer.MAX_VALUE;
//
//            for (int i = 0; i < p.getProcessorEndTimes().length; i++) {
//
//                int maxDataReadyTime = 0;
//                for (Vertex dep : child.getIncomingVertices()) {
//
//                    ScheduledTask depScheduledTask = p.getScheduledTask(dep.getId());
//                    int dataReadyTime = depScheduledTask.getStartTime() + dep.getCost();
//                    if (depScheduledTask.getProcessor() == i) {
//                        // Do nothing, as there is no cross-processor communication delay
//                    } else {
//                        // doing Math.abs to negate virtual edges
//                        dataReadyTime += Math.abs(_dependencyGraph.getEdgeWeight(dep.getId(), child.getId()));
//                    }
//                    maxDataReadyTime = Math.max(maxDataReadyTime, dataReadyTime);
//
//                }
//                minDataReadyTime = Math.min(maxDataReadyTime, minDataReadyTime);
//
//            }
//            drtHeuristic = Math.max(minDataReadyTime + _dependencyGraph.getBottomLevel(child), drtHeuristic);
//
//        }
//
//        return Math.max(idleTimeHeuristic, Math.max(bottomLevelHeuristic, drtHeuristic));
//
//    }

}