import algorithm.UnoptimalAlgo;
import graph.Graph;
import graph.Vertex;
import input.CliParser;
import input.InputParser;
import output.OutputGenerator;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String[] argsInput = {"digraph2.dot", "2"};
        CliParser cliparser = CliParser.getCliParserInstance();
        try {
            cliparser.UI(argsInput);
        } catch ( IllegalArgumentException e ) {
            // in the case that the input cannot be parsed the program is terminated.
            System.err.println(e.getMessage());
            return;
        }

        // get directory of jar
        CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
        File runnableJar = null;

        try {
            runnableJar = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String jarDir = runnableJar.getParentFile().getPath();

        Graph graph = InputParser.readInput(cliparser.getFilePathName(), jarDir);

        UnoptimalAlgo ua = new UnoptimalAlgo();
        ua.computeSchedule(graph);

        OutputGenerator.generate(graph, cliparser.getOutputFileName(), jarDir);

    }
}
