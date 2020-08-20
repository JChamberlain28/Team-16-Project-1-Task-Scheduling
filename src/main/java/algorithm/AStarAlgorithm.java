package algorithm;




import graph.Graph;
import graph.Vertex;

import java.awt.*;
import java.util.List;
import java.util.PriorityQueue;
import java.util.*;


public class AStarAlgorithm {

    private HashMap<PartialSchedule, Float> _heuristicMap = new HashMap<PartialSchedule, Float>();

    private Graph _dependencyGraph;
    private int _numProcessors;

    public AStarAlgorithm(Graph dependencyGraph, int numProcessors){
        _dependencyGraph = dependencyGraph;
        _numProcessors = numProcessors;
    }

    public PartialSchedule findOptimalSchedule() {

        HashSet<HashSet<String>> closedSet = new HashSet<HashSet<String>>();

        PriorityQueue<PartialSchedule> open = new PriorityQueue<PartialSchedule>(
                (a, b) -> {
                    if (_heuristicMap.get(a) > _heuristicMap.get(b)) {
                        return 1;
                    }
                    if (_heuristicMap.get(b) > _heuristicMap.get(a)) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
        );

        PartialSchedule nullSchedule = new PartialSchedule(_dependencyGraph, _numProcessors);
        setHeuristicCost(nullSchedule);
        open.add(nullSchedule);

        while(!open.isEmpty()) {
            PartialSchedule p = open.poll();
            Set<String> s = p.getProcessorStringSet();

            // Maybe apply this dupe check to DFS Branch and Bound
            if (!closedSet.add((HashSet<String>)s)) {
                continue;
            }else if (p.isComplete()) {
                return p;
            }

            List<PartialSchedule> children = p.extend();
            for (PartialSchedule pChild: children ) {

                setHeuristicCost(pChild);
                open.add(pChild);

            }

        }

        return null;

    }

    public void setHeuristicCost(PartialSchedule p) {

        if (_heuristicMap.containsKey(p)) {
            System.err.println("Warning: Heuristic cost already calculated for this partial schedule -> " +
                    "duplicate calculation call");
            return;
        }

        if (p.getScheduledTask() == null){
            _heuristicMap.put(p, 0.0f); // the null schedule is removed immediately from heap so heuristic is irrelevant
            return;
        }

        // Calculate idle time heuristic
        float idleTimeHeuristic = (float) p.getIdleTime();
        for (Vertex v : _dependencyGraph.getVertices()) {
            idleTimeHeuristic += v.getCost();
        }
        idleTimeHeuristic /= p.getProcessorEndTimes().length;

        // Calculate bottom level heuristic
        float bottomLevelHeuristic = 0;
        for (ScheduledTask st : p.getScheduledTasks()){
            bottomLevelHeuristic = Math.max(bottomLevelHeuristic, p.getStartTime() +
                    _dependencyGraph.getBottomLevel(st.getTask()));
        }

        // Calculate Data Ready Time heuristic
        float drtHeuristic = 0;
        for (Vertex child : p.getToSchedule()) {

            int minDataReadyTime = Integer.MAX_VALUE;
            for (int i = 0; i < p.getProcessorEndTimes().length; i++) {


                for (Vertex dep : child.getIncomingVertices()) {

                    ScheduledTask depScheduledTask = p.getScheduledTask(dep.getId());
                    int dataReadyTime = depScheduledTask.getStartTime() + dep.getCost();
                    if (depScheduledTask.getProcessor() == i) {
                        // Do nothing, as there is no cross-processor communication delay
                    } else {
                        dataReadyTime += _dependencyGraph.getEdgeWeight(dep.getId(), child.getId());
                    }
                    minDataReadyTime = Math.min(minDataReadyTime, dataReadyTime);

                }

                int bottomLevel = _dependencyGraph.getBottomLevel(child);
                if (minDataReadyTime == Integer.MAX_VALUE){
                    minDataReadyTime = 0;
                }

                drtHeuristic = Math.max(minDataReadyTime + bottomLevel, drtHeuristic);



            }

        }

        _heuristicMap.put(p, Math.max(Math.max(idleTimeHeuristic, bottomLevelHeuristic), drtHeuristic));



    }

}

//    We maintain two lists: OPEN and CLOSE:
//
//        OPEN consists on nodes that have been visited but not expanded (meaning that sucessors have not been
//        explored yet). This is the list of pending tasks.
//
//        CLOSE consists on nodes that have been visited and expanded (sucessors have been explored already and
//        included in the open list, if this was the case).
//
//        1 Put node_start in the OPEN list with f(node_start) = h(node_start) (initialization)
//        2 while the OPEN list is not empty {
//        3 Take from the open list the node node_current with the lowest
//        4 f(node_current) = g(node_current) + h(node_current)
//        5 if node_current is node_goal we have found the solution; break
//        6 Generate each state node_successor that come after node_current
//        7 for each node_successor of node_current {
//        8 Set successor_current_cost = g(node_current) + w(node_current, node_successor)
//        9 if node_successor is in the OPEN list {
//        10 if g(node_successor) ≤ successor_current_cost continue (to line 20)
//        11 } else if node_successor is in the CLOSED list {
//        12 if g(node_successor) ≤ successor_current_cost continue (to line 20)
//        13 Move node_successor from the CLOSED list to the OPEN list
//        14 } else {
//        15 Add node_successor to the OPEN list
//        16 Set h(node_successor) to be the heuristic distance to node_goal
//        17 }
//        18 Set g(node_successor) = successor_current_cost
//        19 Set the parent of node_successor to node_current
//        20 }
//        21 Add node_current to the CLOSED list
//        22 }
//        23 if(node_current != node_goal) exit with error (the OPEN list is empty)
//        its a generic A* algorithm but we have to maybe change it slightly and implement it according to our graph structure.