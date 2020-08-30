package algorithm;

import graph.Graph;
import graph.Vertex;

import java.util.*;

/*
This algorithm schedule all tasks onto one CPU core sequentially, regardless of the number of cores specified.
It is only guaranteed to produce a valid schedule. This algorithm was used for milestone 1.
This algorithm is not used in the final release but preserved. It also is not integrated with the visualisation
 */
public class SequentialAlgorithm {



    public SequentialAlgorithm() {

    }

    public void computeSchedule(Graph graph){

        List<Integer> roots = graph.getRoots();

        int currentStartTime = 0;

        Set<Integer> visitedIds = new HashSet<>();
        List<Vertex> queue = new ArrayList<Vertex>();

        for (Integer i : roots){
            queue.add(graph.getVertex(i));
        }


        while (queue.size() > 0){ // while there is a vertex in the queue
            // pop first vertex
            Vertex currentVertex = queue.remove(0);

            // check if all parent vertices are scheduled
            boolean parentsScheduled = true;
            for (Vertex v : currentVertex.getIncomingVertices()) {
                boolean parentVisited = visitedIds.contains(v.getId());
                if (!parentVisited){
                    parentsScheduled = false;
                    break;
                }
            }

            if (parentsScheduled && !visitedIds.contains(currentVertex.getId())) {

                visitedIds.add(currentVertex.getId());

                // sets start time for node in graph and updates current start time (directly updating graph)
                currentVertex.setStartTime(currentStartTime);
                currentStartTime += currentVertex.getCost();

                // set processor directly in graph
                currentVertex.setProcessorNumber(1);

                // add all children to queue
                queue.addAll(currentVertex.getOutgoingVertices());

                // add current vertex's label to list of those who have been scheduled
                visitedIds.add(currentVertex.getId());

            }
        }

    }
}