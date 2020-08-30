package output;

import graph.Graph;
import graph.Vertex;
import util.FilenameMethods;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OutputGenerator {

    /**
     * Generates an output .dot file representing a graph with task scheduling information.
     * @param graph Graph object describing the task dependencies
     * @param fileName Name of file to write to ([fileName].dot)
     */
    public static void generate(Graph graph, String fileName, String filePathName) throws IOException {

        System.out.println("PATH: " + filePathName);


        //Files.createDirectories(Paths.get(directoriesForOutput));

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePathName)))) {
            //directoriesForOutput = filePathName.substring(0, filePathName.lastIndexOf(fileName));

            //System.out.println("output file created at directory: " + directoriesForOutput + "with file name: "+ fileName);
            out.write("digraph \"" + fileName.replaceAll(".dot$", "") + "\" {");
            out.newLine();

            // Add all vertices to .dot file
            for (Vertex v : graph.getVertices()) {

                // Vertex format: a [ Weight=2, Start=0, Processor=1];
                String vString = v.getLabel() + " [ Weight=" + v.getCost() + ", " + "Start=" +
                        v.getStartTime() + ", Processor=" + v.getProcessorNumber() + " ];";
                out.write("\t" + vString);
                out.newLine();

                // Add all incoming edges to .dot file
                for (Vertex other : v.getIncomingVertices()) {

                    // Edge format: a −> b [ Weight=1 ];
                    String eString = other.getLabel() + " -> " + v.getLabel() + " [ Weight=" +
                            graph.getEdgeWeight(other.getId(), v.getId()) + " ];";
                    out.write("\t" + eString);
                    out.newLine();

                }
            }

            out.write("}");
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
