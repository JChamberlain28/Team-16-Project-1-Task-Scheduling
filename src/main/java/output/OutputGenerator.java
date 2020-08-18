package output;

import graph.Graph;
import graph.Vertex;
import util.FilenameMethods;

import java.io.*;

public class OutputGenerator {

    /**
     * Generates an output .dot file representing a graph with task scheduling information.
     * @param graph Graph object describing the task dependencies
     * @param fileName Name of file to write to ([fileName].dot)
     */
    public static void generate(Graph graph, String fileName) {
        String dir = FilenameMethods.getDirectoryOfJar();
        String path = (dir + File.separator + fileName);
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)))) {

            // TODO: Determine proper digraph naming scheme. Assume same as file name for now
            out.write("digraph \"" + fileName.replaceAll(".dot$", "") + "\" {");
            out.newLine();

            for (Vertex v : graph.getVertices()) {

                // Vertex format: a [ Weight=2, Start=0, Processor=1];
                String vString = v.getId() + " [ Weight=" + v.getCost() + ", " + "Start=" +
                        v.getStartTime() + ", Processor=" + v.getProcessorNumber() + " ];";
                out.write("\t" + vString);
                out.newLine();

                for (Vertex other : v.getIncomingVertices()) {

                    // Edge format: a −> b [ Weight=1 ];
                    String eString = other.getId() + " -> " + v.getId() + " [ Weight=" +
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
