package input;

import graph.Graph;
import graph.Vertex;
import util.FilenameMethods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/*
 * The CliParser class will parse the specified input ".dot" file and store the
 * given data as a graph representation.
 * The graph object is then use throughout the program in the execution of the algorithm.
 * */
public class InputParser {

    public static Graph readInput(String fileName) {

        Graph algoGraph = new Graph("newGraph");
        int edgeCount = 0;

        BufferedReader bufferReader = null;

        String dir = FilenameMethods.getDirectoryOfJar();
        File file = new File(dir + File.separator + fileName);

            try {
            bufferReader = new BufferedReader(new FileReader(file));

            String line = bufferReader.readLine();
            // Remove all lines until it finds the last line of the title,
            // which is the first line with a vertex.
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

                } else {
                    throw new IllegalArgumentException("Error: Input '.dot' file is not a valid graph. " +
                            "The input dot file should be valid.");
                }
                line = bufferReader.readLine();
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {

            try {
                bufferReader.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return null;
            }
        }

        return algoGraph;

    }
}








