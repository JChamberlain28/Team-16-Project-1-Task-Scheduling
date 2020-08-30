import algorithm.*;
import graph.Graph;


import graph.GraphCopier;
import input.CliParser;
import input.InputParser;
import output.OutputGenerator;
import visualisation.Visualise;

import java.io.IOException;


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

        Algorithm  algorithm = new DfsBranchAndBound(graph, cliparser.getNumberOfProcessors(),
                cliparser.getNumberOfCores());


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
