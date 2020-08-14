package input;

import algorithm.UnoptimalAlgo;
import graph.Graph;
import output.OutputGenerator;

import java.io.*;
import java.net.URISyntaxException;
import java.security.CodeSource;


//import org.apache.commons.cli.*;


public class CliParser {


    private static final CliParser CliParsedInputs = new CliParser();
    private String filePathName;
    private int numberOfProcessors;
    private String outputFileName;
    private boolean visualisationDisplay;



    private CliParser(){
    }




    public static void UI(String[] args) {
        if (args.length == 0) {
            System.err.println("No arguments provided");
        }
        else {
            System.out.println("SOFTENG306 Project 1");
            System.out.println("group16 - Saddboys");

            handleInput(args);
        }
    }

    private static void handleInput(String[] args) {

        // check for all the strings validity
        String[] result = parseCli(args); // result[0]: file path, result[1]: num. processors, result[2]: output file, result[3]: num. cores, result[4]: visualize

        if (result != null) {
            try {

/*                //main method here.
                // get directory of jar
                // wtf?
                CodeSource codeSource = CliParser.class.getProtectionDomain().getCodeSource();
                File runnableJar = null;

                try {
                    runnableJar = new File(codeSource.getLocation().toURI().getPath());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                String jarDir = runnableJar.getParentFile().getPath();


                //make graph
                Graph graph = InputParser.readInput(result[0], jarDir);

                //run algo
                // use 2nd parameter for algo
                // method to select whih algo
               UnoptimalAlgo ua = new UnoptimalAlgo();
                ua.computeSchedule(graph);

                //visualisation?
                // option for visualisation
                // method to enable or disable visualisation







                //option to add output fie name
                // need a method to create Output File Name.
               //OutputGenerator.generate(graph, result[2], jarDir);*/



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    private static String[] parseCli(String[] args) {





        String[] result = new String[5];

        // method to make new output file name should be added
        CliParsedInputs.outputFileName = "output.dot"; //default output name
        CliParsedInputs.visualisationDisplay = false;  // default visualisation boolean (true or false)

        // Mandatory options
        if (args.length > 1) { // If both file path and number of processors are entered
            if (args[0].endsWith(".dot")) {
                CliParsedInputs.filePathName = args[0]; // File path
            } else {
                //throw exception here?
                System.err.println("Invalid file path name");
                result = null;
            }


            if (result != null){
                // number of processors
                if ( checkValidStringInt(args[1])) {
                    CliParsedInputs.numberOfProcessors = Integer.parseInt(args[1]);
                } else {
                    System.err.println("Invalid number of processors");
                }



                // visualisation
                if (checkValidBoolean(args[4])){
                    CliParsedInputs.visualisationDisplay = Boolean.parseBoolean(args[4]);
                }  else {
                    System.err.println("Invalid visualisation parameter");
                }

            } else {
                // null result due to invalid input file name
            }
        } else {
            // does not have minimum number of args.
            System.err.println("0 arguments");
        }

        return result;
    }

    // get object with parsed inputs
    public static CliParser getCliParserInstance(){
        return CliParsedInputs;
    }


    public String getFilePathName() {
        return filePathName;
    }

    public int getNumberOfProcessors() {
        return numberOfProcessors;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public boolean isVisualisationDisplay() {
        return visualisationDisplay;
    }



    public static boolean checkValidBoolean(String booleanString){

        return(booleanString.equalsIgnoreCase("true") || booleanString.equalsIgnoreCase("false"));

    }


    public static boolean checkValidStringInt(String intString) {
        try {
            if (Integer.parseInt(intString) >= 1){
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

    }









    /*
    *
    *
    *
    *
    *
    *
    * https://stackoverflow.com/questions/11704338/java-cli-commandlineparser
    * ...
Option opt1 = OptionBuilder.hasArgs(1).withArgName("output directory")
    .withDescription("This is the output directory").isRequired(true)
    .withLongOpt("output").create("O");

Option opt2 = OptionBuilder.hasArgs(1).withArgName("file name")
    .withDescription("This is the file name").isRequired(true)
    .withLongOpt("name").create("N")

Options o = new Options();
o.addOption(opt1);
o.addOption(opt2);
CommandLineParser parser = new BasicParser();

try {
  CommandLine line = parser.parse(o, args); // args are the arguments passed to the  the application via the main method
  if (line.hasOption("output") {
     //do something
  } else if(line.hasOption("name") {
     // do something else
  }
} catch(Exception e) {
  e.printStackTrace();
}
    *
    *
    *
    *
    *
    *
    *
    * */


















}
