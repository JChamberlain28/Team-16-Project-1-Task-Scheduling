package input;

import graph.Edge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import graph.Vertex;
import graph.Graph;

public class InputParser {

    private int[][] listOfEdges;
    Graph graph = new Graph("Falcon1");


    public static void readInput() {

        int edgeCount = 0;



        BufferedReader bufferReader = null;
        try {
            File file = new File("C:\\Users\\a_sid\\IdeaProjects\\project-1-saadboys-16\\src\\main\\java\\input\\digraph2.dot");
            bufferReader = new BufferedReader(new FileReader(file));

            String line = bufferReader.readLine();


            while ((line = bufferReader.readLine()) != null) {

        System.out.println(line);

        if (line.substring(0, 1).equals("}")) {
        break;
        }


        if (line.contains(">")) { // edges

        char parentNode =  line.charAt(0);
        char childNode = line.charAt(5);
//        System.out.println(parentNode+" "+ childNode);
            int weight = Integer.parseInt(Character.toString(line.charAt(15)));
            String edgeId = parentNode+"->"+childNode;

            graph.getHashVertices().
            Edge edge = new Edge();
            graph.addEdge(edgeIdid, edge);








        } else if (!line.contains("->")) { // nodes


        } else { //end of file
        System.out.println("end of file or GG");

        }


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


        }
        }








