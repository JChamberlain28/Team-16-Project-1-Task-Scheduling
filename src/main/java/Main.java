import algorithm.*;
import graph.Graph;


import graph.GraphCopier;
import input.CliParser;
import input.InputParser;
import javafx.application.Platform;
import javafx.stage.Stage;
import output.OutputGenerator;
import visualisation.Visualise;
import visualisation.controllers.GUIController;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Main {
    public static void main(String[] args) {


        // singleton class
        CliParser cliparser = CliParser.getCliParserInstance();

        // Parse the command line inputs and check for validity of all inputs
        try {
            cliparser.UI(args);

            if (!cliparser.getSuccessfulCliParse()){
                // in the case that we should not run the algorithm
                return;
            }
        } catch ( IllegalArgumentException e ) {
            // in the case that the input cannot be parsed the program is terminated.
            System.err.println(e.getMessage());
            return;
        }

        // Parse the input file and create the graph object
        Graph graph = InputParser.readInput(cliparser.getFilePathName());
        // preserve the original graph before adding virtual edges as it is used for output generation
        Graph originalGraph = GraphCopier.copyGraph(graph);

        // create virtual edges to enforce task order for identical tasks (one pruning method)
        graph.buildVirtualEdges();

        // choose DFS algorithm to use
        Algorithm algorithm;
        if (cliparser.getNumberOfCores() < 2) { // if less than 2 threads to be used for algorithm
                                                // use sequential algorithm
            algorithm = new DfsBranchAndBound(graph, cliparser.getNumberOfProcessors());

        } else { // use parallelised algorithm
            algorithm = new ParallelisedDfsBranchAndBound(graph, cliparser.getNumberOfProcessors(),
                    cliparser.getNumberOfCores());
        }

        if (cliparser.isVisualisationDisplay()) {

            (new Thread() {
                @Override
                public void run() {
                    Visualise.startVisual(args, algorithm, graph);
                }
            }).start();
        }

        PartialSchedule schedule = algorithm.findOptimalSchedule();

        // persist start times and processor numbers in the graph for use in output
        for (ScheduledTask st : schedule.getScheduledTasks()) {
            st.updateVertex(originalGraph);
        }


        // Create output with the output file.
        try {
            OutputGenerator.generate(originalGraph, cliparser.getOutputFileName(), cliparser.getOutputFilePath());
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

}
