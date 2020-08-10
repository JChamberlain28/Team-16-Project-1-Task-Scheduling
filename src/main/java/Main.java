import algorithm.UnoptimalAlgo;
import graph.Graph;
import graph.Vertex;
import input.InputParser;
import output.OutputGenerator;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // get directory of jar
        CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
        File runnableJar = null;

        try {
            runnableJar = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String jarDir = runnableJar.getParentFile().getPath();

        Graph graph = InputParser.readInput("digraph2.dot", jarDir);

        UnoptimalAlgo ua = new UnoptimalAlgo();
        ua.computeSchedule(graph);

        OutputGenerator.generate(graph, "sampleFile", jarDir);

    }
}
