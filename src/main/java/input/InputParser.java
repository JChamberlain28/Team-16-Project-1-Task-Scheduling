package input;


import graph.Edge;

import graph.Graph;
import graph.Vertex;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import graph.Vertex;
import graph.Graph;

public class InputParser {

    private int[][] listOfEdges;


    public static Graph  readInput(String fileName, String dir) {


        Graph algoGraph = new Graph("test"); //TODO: consider using split at dot to get name without .dot


        int edgeCount = 0;



        BufferedReader bufferReader = null;
        try {
            File file = new File(dir + File.separator + fileName);
            bufferReader = new BufferedReader(new FileReader(file));

            String line = bufferReader.readLine(); //need to remove first line somehow.



            while ((line = bufferReader.readLine()) != null) {

                System.out.println(line);

                // end of file
                if (line.substring(0, 1).equals("}")) {
                    break;
                }


                line = line.trim();
                line = line.replaceAll("\\s+","");

                if (line.contains(">")) { // edges

                    //split the line into child and parent vertex id and edge weight
                    // in form: child and parent vertex id , edge weight
                    String[] lineSplitEdgeAndWeight = line.split("\\[Weight=");

                    String childAndParentVertexID = lineSplitEdgeAndWeight[0];
                    String edgeWeight = lineSplitEdgeAndWeight[1];

                    //split the childAndParentVertexID into child and parent vertex id
                    // in form: child vertex id,  parent vertex id
                    String[] childAndParentVertexIDSplit = childAndParentVertexID.split(">");

                    // find vertex id of child and parent
                    String parentVertexID = childAndParentVertexIDSplit[0];
                    parentVertexID = parentVertexID.replaceAll("[^A-Za-z0-9 -]", "");
                    String childVertexID = childAndParentVertexIDSplit[1];
                    childVertexID = childVertexID.replaceAll("[^A-Za-z0-9 -]", "");

                    // find edge weight
                    edgeWeight = edgeWeight.replaceAll("[^-?0-9]+", "");
                    int edgeWeightInt = Integer.parseInt(edgeWeight);

                    // create edge id
                    String edgeId = parentVertexID+"->"+childVertexID;


                    // Find parent and child vertices for edge
                    HashMap<String, Vertex> hashVertices = algoGraph.getHashVertices();
                    Vertex parent = hashVertices.get(parentVertexID);
                    Vertex child = hashVertices.get(childVertexID);

                    //instantiate edge
                    Edge edge = new Edge(parent, child, edgeWeightInt);

                    // add edge to graph data.
                    algoGraph.addEdge(edgeId, edge);


                    edgeCount++;


                    // adding incoming edges and outgoing edges for each vertex for faster searching
                    parent.addOutgoingEdge(edge);
                    child.addIncomingEdge(edge);

                    // adding incoming vertices and outgoing vertices for each vertex for faster searching
                    parent.addOutgoingVertex(edge.getEndVertex());
                    child.addIncomingVertex(edge.getStartVertex());


                } else if (!line.contains(">")) { // nodes



                    //split the line into vertex id and vertex weight
                    // in form: id , weight
                    String[] lineSplit = line.split("\\[Weight=");

                    // find vertex id
                    String vertexID = lineSplit[0];
                    vertexID = vertexID.replaceAll("[^A-Za-z0-9 -]", "");


                    // find vertex weight
                    String vertexWeight = lineSplit[1];
                    vertexWeight = vertexWeight.replaceAll("[^-?0-9]+", "");
                    int vertexWeightInt = Integer.parseInt(vertexWeight);


                    // Instantiate vertex using ID and weight
                    // and then add to the graph.
                    Vertex graphVertex = new Vertex(vertexID, vertexWeightInt);
                    algoGraph.addVertex(vertexID, graphVertex);


                } else { //end of file
                    System.out.println("end of file or GG");

                }


            }

            // checking algo
           System.out.println(algoGraph.getHashVertices());
            System.out.println(algoGraph.getHashEdges());
            Vertex vertexB = algoGraph.getHashVertices().get("b");
            System.out.println("incomming edges = " + vertexB.getIncomingEdges());
            System.out.println("OutgoingVertices = " + vertexB.getOutgoingVertices());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return algoGraph;


    }
}








