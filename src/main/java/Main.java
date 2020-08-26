import algorithm.AStarAlgorithm;
import algorithm.PartialSchedule;
import algorithm.ScheduledTask;
import graph.Graph;


import input.CliParser;
import input.InputParser;
import output.OutputGenerator;


public class Main {
    public static void main(String[] args) {

        CliParser cliparser = CliParser.getCliParserInstance();

        // Parse the command line inputs and check for validity of all inputs
        try {
            cliparser.UI(args);
        } catch ( IllegalArgumentException e ) {
            // in the case that the input cannot be parsed the program is terminated.
            System.err.println(e.getMessage());
            return;
        }

        // Parse the input file and create the graph object
        Graph graph = InputParser.readInput(cliparser.getFilePathName());
        graph.IdenticalTaskListBuild();
        AStarAlgorithm aStar = new AStarAlgorithm(graph, cliparser.getNumberOfProcessors());
        PartialSchedule schedule = aStar.findOptimalSchedule();

        // persist start times and processor numbers in the graph for use in output
        for (ScheduledTask st : schedule.getScheduledTasks()){
            st.updateVertex(graph);
        }
        // Create output with the output file.
        OutputGenerator.generate(graph, cliparser.getOutputFileName());

    }

}
