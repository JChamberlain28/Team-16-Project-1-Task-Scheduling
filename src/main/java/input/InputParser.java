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
    Graph graph = new Graph("Falcon1");


    public static void  readInput() {


        Graph algoGraph = new Graph("newGraph");


        int edgeCount = 0;



        BufferedReader bufferReader = null;
        try {
            File file = new File("C:\\Users\\a_sid\\IdeaProjects\\project-1-saadboys-16\\src\\main\\java\\input\\digraph2.dot");
            bufferReader = new BufferedReader(new FileReader(file));

            String line = bufferReader.readLine(); //need to remove first line somehow.



            while ((line = bufferReader.readLine()) != null) {

        System.out.println(line);

        if (line.substring(0, 1).equals("}")) {
        break;
        }

                // end of file
                if (line.substring(0, 1).equals("}")) {
                    break;
                }


        if (line.contains(">")) { // edges

        char parentNode =  line.charAt(0);
        char childNode = line.charAt(5);
//        System.out.println(parentNode+" "+ childNode);
            int weight = Integer.parseInt(Character.toString(line.charAt(15)));
            String edgeId = parentNode+"->"+childNode;


            System.out.println("edgeId ="+edgeId);
            System.out.println("edgeWeight ="+weight);


            HashMap<String, Vertex> hashVertices = algoGraph.getHashVertices();
            Vertex parent = hashVertices.get(parentNode);
            Vertex child = hashVertices.get(childNode);
            Edge edge = new Edge(parent, child, weight);

            System.out.println(edge);

            algoGraph.addEdge(edgeId, edge);

            edgeCount++;



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
//            System.out.println(algoGraph.getHashVertices());

            System.out.println(algoGraph.getHashEdges());

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








