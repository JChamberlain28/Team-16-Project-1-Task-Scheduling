package input;

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

    public static Graph readInput(String fileName, String dir) {

        Graph algoGraph = new Graph("newGraph");
        int edgeCount = 0;

        BufferedReader bufferReader = null;
        try {
            File file = new File(dir + File.separator + fileName);
            bufferReader = new BufferedReader(new FileReader(file));



            String line = bufferReader.readLine();
            //remove all lines until it finds the last line of the title
            while (!line.contains("[Weight=")) {
                line = bufferReader.readLine();
            }

            while (line!= null) {

                // end of file
                if (line.length()==1 && line.contains("}")) {
                    break;
                }

                line = line.trim();
                line = line.replaceAll("\\s+", "");

                if (line.contains(">") && line.contains("[Weight=")) { // edges

                    //split the line into child and parent vertex id and edge weight
                    // in form: child and parent vertex id , edge weight
                    String[] lineSplitEdgeAndWeight = line.split("\\[Weight=");

                    String childAndParentVertexID = lineSplitEdgeAndWeight[0];
                    String edgeWeight = lineSplitEdgeAndWeight[1];

                    //split the childAndParentVertexID into child and parent vertex id
                    // in form: child vertex id,  parent vertex id
                    String[] childAndParentVertexIDSplit = childAndParentVertexID.split("->");

                    // find vertex id of child and parent
                    String parentVertexID = childAndParentVertexIDSplit[0];
                    System.out.println(parentVertexID);
                    String childVertexID = childAndParentVertexIDSplit[1];

                    // find edge weight
                    edgeWeight = edgeWeight.replaceAll("[^-?0-9]+", "");
                    int edgeWeightInt = Integer.parseInt(edgeWeight);

                    // add edge to graph data.
                    algoGraph.addEdge(parentVertexID, childVertexID, edgeWeightInt);
                    edgeCount++;

                } else if (!line.contains(">") && line.contains("[Weight=")) { // nodes

                    //split the line into vertex id and vertex weight
                    // in form: id , weight
                    String[] lineSplit = line.split("\\[Weight=");

                    // find vertex id
                    String vertexID = lineSplit[0];

                    // find vertex weight
                    String vertexWeight = lineSplit[1];
                    vertexWeight = vertexWeight.replaceAll("[^-?0-9]+", "");
                    int vertexWeightInt = Integer.parseInt(vertexWeight);

                    // Instantiate vertex using ID and weight
                    // and then add to the graph.
                    Vertex graphVertex = new Vertex(vertexID, vertexWeightInt);
                    algoGraph.addVertex(vertexID, graphVertex);

                } else { // end of file
                    System.out.println("end of file or GG");
                }
                line = bufferReader.readLine();
            }

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








