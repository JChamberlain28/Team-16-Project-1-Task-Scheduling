import algorithm.PartialSchedule;
import algorithm.UnoptimalAlgo;
import graph.Graph;
import graph.Vertex;
import input.InputParser;
import output.OutputGenerator;
import output.GraphWriter;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import algorithm.ParallelisedDfsBranchAndBound;
import algorithm.helperForRunnable;

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

        Graph graph = InputParser.readInput("test.dot", jarDir);

//        UnoptimalAlgo ua = new UnoptimalAlgo();
//        ua.computeSchedule(graph);

        ParallelisedDfsBranchAndBound p = new ParallelisedDfsBranchAndBound(graph, 2, 4);


        PartialSchedule finalSchedule = p.findOptimalSchedule();



        GraphWriter g = new GraphWriter();
        g.setGraph(finalSchedule);

        OutputGenerator.generate(graph, "sampleFile", jarDir);

    }
}
