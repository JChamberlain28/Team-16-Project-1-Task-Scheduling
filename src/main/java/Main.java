import algorithm.*;
import graph.Graph;


import input.CliParser;
import input.InputParser;
import output.OutputGenerator;
import visualisation.Visualise;
import visualisation.controllers.GUIController;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Main {
    public static void main(String[] args) {

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
        graph.buildVirtualEdges();

        Algorithm dfs = new ParallelisedDfsBranchAndBound(graph, cliparser.getNumberOfProcessors(),
                cliparser.getNumberOfCores());
        PartialSchedule schedule = dfs.findOptimalSchedule();

        // persist start times and processor numbers in the graph for use in output
        for (ScheduledTask st : schedule.getScheduledTasks()){
            st.updateVertex(graph);
        }


        if (cliparser.isVisualisationDisplay()) {

            Visualise.startVisual(args);
        }



        // Create output with the output file.
        try {
            OutputGenerator.generate(graph, cliparser.getOutputFileName(), cliparser.getOutputFilePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Ended: " + LocalDateTime.now());

    }

}
