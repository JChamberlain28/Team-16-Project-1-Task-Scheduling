package algorithm;

import graph.Graph;
import graph.Vertex;

import java.util.*;

//TODO: change methods not to use edges from graph
public class UnoptimalAlgo {

    public void computeSchedule(Graph graph){

        Set<String> visitedVertexLabels = new HashSet<String>();

        int currentStartTime = 0;

        Set<String> visitedIds = new HashSet<>();
        List<Vertex> queue = new ArrayList<Vertex>();
        queue.addAll(graph.getRoots());

        while (queue.size() > 0){ // while there is a vertex left to be processed

            Vertex currentVertex = queue.remove(0);

            // check if all parent vertices are scheduled
            boolean parentsScheduled = true;
            for (Vertex v : currentVertex.getIncomingVertices()) {
                boolean parentVisited = visitedVertexLabels.contains(v.getId());
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
                queue.addAll(currentVertex.getOutgoingVertices());

                // add current vertex's label to list of those who have been scheduled
                visitedVertexLabels.add(currentVertex.getId());

            }

        }

    }
}

