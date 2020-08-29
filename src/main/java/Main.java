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
        String[] inputArgs = {"C:\\Users\\dh\\eclipse-workspace\\project-1-saadboys-16\\src\\main\\java\\input\\digraph2.dot", "4", "-v", "-p", "4"};
        System.out.println("Started: " + LocalDateTime.now());

        CliParser cliparser = CliParser.getCliParserInstance();

        // Parse the command line inputs and check for validity of all inputs
        try {
            //cliparser.UI(args);
            cliparser.UI(inputArgs);
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
        Graph originalGraph = GraphCopier.copyGraph(graph);
        graph.buildVirtualEdges();

        Algorithm dfs = new ParallelisedDfsBranchAndBound(graph, cliparser.getNumberOfProcessors(),
                cliparser.getNumberOfCores());



        if (cliparser.isVisualisationDisplay()) {

            (new Thread() {
                @Override
                public void run() {
                    Visualise.startVisual(inputArgs, dfs, graph);
                }
            }).start();

        }

        PartialSchedule schedule = dfs.findOptimalSchedule();

        // persist start times and processor numbers in the graph for use in output
        for (ScheduledTask st : schedule.getScheduledTasks()) {
            st.updateVertex(originalGraph);
        }


        // Create output with the output file.
        OutputGenerator.generate(originalGraph, cliparser.getOutputFileName());

        System.out.println("Ended: " + LocalDateTime.now());

    }

}
