import algorithm.*;
import graph.Graph;


import input.CliParser;
import input.InputParser;
import output.OutputGenerator;
import visualisation.Visualise;

import java.io.IOException;



public class Main {
    public static void main(String[] args) {
        //String[] inputArgs = {  "digraph2.dot", "2", "-o", "C:\\Users\\dh\\eclipse-workspace\\project-1-saadboys-16\\src\\main\\java\\outputfiles\\digraph2-output.dot"  };
        String[] inputArgs = {  "digraph2.dot", "2","-o", "digraph22.dot" };
        //String[] inputArgs = {  "\\project-1-saadboys-16\\src\\main\\java\\input\\digraph2.dot", "2" };
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
//        AStarAlgorithm aStar = new AStarAlgorithm(graph, cliparser.getNumberOfProcessors());
//        PartialSchedule schedule = aStar.findOptimalSchedule();
            DfsBranchAndBound dfs = new DfsBranchAndBound(graph, cliparser.getNumberOfProcessors());
            PartialSchedule schedule = dfs.findOptimalSchedule();

//        ParallelisedDfsBranchAndBound pdbb = new ParallelisedDfsBranchAndBound(graph, cliparser.getNumberOfProcessors(), 8);
//        PartialSchedule schedule = pdbb.findOptimalSchedule();
        if (cliparser.isVisualisationDisplay()) {
            Visualise.startVisual(args);
        }

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



    }

}
