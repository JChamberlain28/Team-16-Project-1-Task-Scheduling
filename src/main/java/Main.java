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

        CliParser cliparser = new CliParser();
        cliparser.UI(args);

        System.out.println(cliparser.getCliParserInstance().getFilePathName());
        System.out.println(cliparser.getCliParserInstance().getNumberOfCores());
        System.out.println(cliparser.getCliParserInstance().getNumberOfProcessors());
        System.out.println(cliparser.getCliParserInstance().getOutputFileName());
        System.out.println(cliparser.getCliParserInstance().isVisualisationDisplay());


       /* // get directory of jar
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

        OutputGenerator.generate(graph, "sampleFile", jarDir);*/






    }
}
