package algorithm;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.*;

//TODO: change methods not to use edges from graph
public class UnoptimalAlgo {



    public UnoptimalAlgo() {

    }

    public void computeSchedule(Graph graph){

        Set<String> visitedVertexLabels = new HashSet<String>();
        HashMap<String, Vertex> vertices = graph.getHashVertices(); // change method when refactor graph
        // assume root is a for now (add get root method to graph)
        Vertex root = vertices.get("a");

        int currentStartTime = 0;

        List<Vertex> queue = new ArrayList<Vertex>();

        queue.add(root);

        while (queue.size() > 0){ // while there is a vertex in the queue
            // pop first vertex
            Vertex currentVertex = queue.remove(0);

            //check if all perant vertices are scheduled
            boolean allScheduled = true;
            for (Edge e : currentVertex.getIncomingEdges()) {
                boolean contains = visitedVertexLabels.contains(e.getStartVertex().getId());
                if (!contains){
                    allScheduled = false;
                    break;
                }

            }

            if (allScheduled){
                // sets start time for node in graph and updates current start time (directly updating graph)
                currentVertex.setStartTime(currentStartTime);

                currentStartTime += currentVertex.getCost();

                // set processor directly in graph
                currentVertex.setProcessorNumber(1); //TODO: we think its 1

                // add all children to queue
                //TODO: perhaps this is why they have 2 ways of getting out/in vertices, dont need to iterate through edges to invoke get vertex
                queue.addAll(currentVertex.getOutgoingVertices());

                // add current vertex's label to list of those who have been scheduled
                visitedVertexLabels.add(currentVertex.getId());
            }
        }


    }
}

