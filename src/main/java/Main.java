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


        // args[0]= filepath
        // args1]= number processor
        // args[2]= outputfile
        // args[3]= number cores
        // args [4] = visulisation boolean


        // args [4] = visulisation boolean

        // temp input
        // temp inputs that should be given in command line
        String [] input = new String[5];
        input[0] = "digraph2.dot";
        input[1] = "3";
        input[2] =  "output.dot";
        input[3] =  "16";
        input[4] =  "true";

        CliParser.UI(input);

    }
}
