package algorithm;

import graph.Graph;
import graph.Vertex;

import java.util.*;

/*
This algorithm schedule all tasks onto one CPU core sequentially, regardless of the number of cores specified.
It is only guaranteed to produce a valid schedule.
 */
public class SequentialAlgorithm {



    public SequentialAlgorithm() {

    }

    public void computeSchedule(Graph graph){

        Set<String> visitedVertexLabels = new HashSet<String>();
        Vertex root = graph.getRoot();

        int currentStartTime = 0;

        Set<String> visitedIds = new HashSet<>();
        List<Vertex> queue = new ArrayList<Vertex>();

        queue.add(root);

        while (queue.size() > 0){ // while there is a vertex in the queue
            // pop first vertex
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
                visitedVertexLabels.add(currentVertex.getId());

            }
        }

    }
}