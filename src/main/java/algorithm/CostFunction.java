package algorithm;

import graph.Vertex;
import graph.Graph;

public class CostFunction {

    public static float getHeuristicCost(PartialSchedule p, Graph graph) {

        if (p.getScheduledTask() == null){
            return 0.0f;
        }

        // Calculate idle time heuristic
        float idleTimeHeuristic = (float) p.getIdleTime();
        for (Vertex v : graph.getVertices()) {
            idleTimeHeuristic += v.getCost();
        }
        idleTimeHeuristic /= p.getProcessorEndTimes().length;

        // Calculate bottom level heuristic
        float bottomLevelHeuristic = 0;
        for (ScheduledTask st : p.getScheduledTasks()){
            bottomLevelHeuristic = Math.max(bottomLevelHeuristic, st.getStartTime() +
                    graph.getBottomLevel(st.getTask()));
        }

        // Calculate Data Ready Time heuristic
        float drtHeuristic = 0;
        for (int childId : p.getToSchedule()) {

            Vertex child = graph.getVertex(childId);
            float minDataReadyTime = Integer.MAX_VALUE;

            for (int i = 0; i < p.getProcessorEndTimes().length; i++) {

                int maxDataReadyTime = 0;
                for (Vertex dep : child.getIncomingVertices()) {

                    ScheduledTask depScheduledTask = p.getScheduledTask(dep.getId());
                    int dataReadyTime = depScheduledTask.getStartTime() + dep.getCost();
                    if (depScheduledTask.getProcessor() == i) {
                        // Do nothing, as there is no cross-processor communication delay
                    } else {
                        // doing Math.abs to negate virtual edges
                        dataReadyTime += Math.abs(graph.getEdgeWeight(dep.getId(), child.getId()));
                    }
                    maxDataReadyTime = Math.max(maxDataReadyTime, dataReadyTime);

                }
                minDataReadyTime = Math.min(maxDataReadyTime, minDataReadyTime);

            }
            drtHeuristic = Math.max(minDataReadyTime + graph.getBottomLevel(child), drtHeuristic);

        }

        return Math.max(idleTimeHeuristic, Math.max(bottomLevelHeuristic, drtHeuristic));

    }
}
