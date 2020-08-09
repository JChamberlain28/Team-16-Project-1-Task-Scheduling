package input;

import graph.Graph;
import graph.Vertex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class InputParser {


    public static void  readInput() {


        Graph algoGraph = new Graph("newGraph");


        BufferedReader bufferReader = null;
        try {
            File file = new File("C:\\Users\\dh\\eclipse-workspace\\project-1-saadboys-16\\src\\main\\java\\input\\digraph2.dot");
            bufferReader = new BufferedReader(new FileReader(file));

            String line = bufferReader.readLine(); //need to remove first line somehow.



            while ((line = bufferReader.readLine()) != null) {

                System.out.println(line);



                // end of file
                if (line.substring(0, 1).equals("}")) {
                    break;
                }


                if (line.contains("->")) { // edges




                } else if (!line.contains("->")) { // nodes


                    //may move these outside the if statements to use with vertex
                    line = line.trim();
                    line.replaceAll( "\\s+", "");



                    String vertexID = line.substring(0, 1);
                    String vertexWeight = line.split("\\[Weight=")[1];
                    vertexWeight = vertexWeight.replaceAll("[^-?0-9]+", "");
                    int vertexWeightInt = Integer.parseInt(vertexWeight);

                    System.out.println(vertexID);
                    System.out.println(vertexWeight);

                    Vertex graphVertex = new Vertex(vertexID, vertexWeightInt);

                    System.out.println(graphVertex);

                    algoGraph.addVertex(vertexID, graphVertex);



                } else { //end of file
                    System.out.println("end of file or GG");

                }


                }

            // checking algo
            System.out.println(algoGraph.getHashVertices());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}








