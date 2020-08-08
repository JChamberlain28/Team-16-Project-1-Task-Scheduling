package output;

import graph.Graph;
import graph.Vertex;
import graph.Edge;

import java.io.*;
import java.util.List;

public class OutputGenerator {

    /**
     * Generates an output .dot file representing a graph with task scheduling information.
     * @param vertices List of vertices in the order they were parsed from the .dot input file
     * @param fileName Name of file to write to ([fileName].dot)
     */
    public static void generate(List<Vertex> vertices, String fileName) {

        String path = System.getProperty("user.dir") + File.separator + fileName + ".dot";
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)))) {

            // TODO: Determine proper digraph naming scheme. Assume same as file name for now
            out.write("digraph \"" + fileName + "\" {");
            out.newLine();

            for (Vertex v : vertices) {

                // Vertex format: a [ Weight=2, Start=0, Processor=1];
                String vString = v.getId() + " [ Weight=" + v.getCost() + ", " + "Start=" +
                        v.getStartTime() + ", Processor=" + v.getProcessorNumber() + " ];";
                out.write("\t" + vString);
                out.newLine();

                for (Edge e : v.getIncomingEdges()) {

                    // Edge format: a −> b [ Weight=1 ];
                    String eString = e.getStartVertex().getId() + " -> " + v.getId() + " [ Weight=" + e.getWeight() + " ];";
                    out.write("\t" + eString);
                    out.newLine();

                }

            }

            out.write("}");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
