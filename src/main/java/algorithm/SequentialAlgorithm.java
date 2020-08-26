package algorithm;

import graph.Graph;
import graph.Vertex;

import java.util.*;

public class SequentialAlgorithm {

    public void computeSchedule(Graph graph){

        Set<Integer> visitedIds = new HashSet<Integer>();
        List<Integer> queue = new ArrayList<Integer>();
        queue.addAll(graph.getRoots());

        int currentStartTime = 0;
        while (queue.size() > 0){ // while there is a vertex left to be processed

            Vertex currentVertex = graph.getVertex(queue.remove(0));

            // check if all parent vertices are scheduled
            boolean parentsScheduled = true;
            for (Vertex v : currentVertex.getIncomingVertices()) {
                boolean parentVisited = visitedIds.contains(v.getId());
                if (!parentVisited){
                    parentsScheduled = false;
                    break;
                }
            }

            if (parentsScheduled && !visitedIds.contains(currentVertex.getId())) {  // if all dependencies are scheduled

                visitedIds.add(currentVertex.getId());

                // sets start time for node in graph and updates current start time (directly updating graph)
                currentVertex.setStartTime(currentStartTime);
                currentStartTime += currentVertex.getCost();

                // set processor directly in graph
                currentVertex.setProcessorNumber(1);

                // add all children to queue
                for (Vertex child : currentVertex.getOutgoingVertices()) {
                    queue.add(child.getId());
                }

                // add current vertex's label to list of those who have been scheduled
                visitedIds.add(currentVertex.getId());

            }

        }

    }
}

