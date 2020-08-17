package input;

import algorithm.UnoptimalAlgo;
import graph.Graph;
import output.OutputGenerator;

import java.io.*;
import java.net.URISyntaxException;
import java.security.CodeSource;


import org.apache.commons.cli.*;


public class CliParser {


    private static final CliParser CliParsedInputs = new CliParser();

    private String filePathName;
    private int numberOfProcessors;
    private int numberOfCores;
    private String outputFileName;
    private boolean visualisationDisplay;



    public void UI(String[] args) {
        if (args.length == 0) {
            System.err.println("No arguments provided");
        }
        else {
            System.out.println("SOFTENG306 Project 1");
            System.out.println("group16 - Saddboys");

            handleInput(args);
        }
    }

    private void handleInput(String[] args) {

        // check for all the strings validity
        parseCli(args);

        /*if (result != null) {
            try {

              //main method here.
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
               //OutputGenerator.generate(graph, result[2], jarDir);



            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }




    private void parseCli(String[] args) {

        // method to make new output file name should be added
        CliParsedInputs.numberOfCores = 1;  //default number of cores
        CliParsedInputs.outputFileName = "output.dot"; //default output name
        CliParsedInputs.visualisationDisplay = false;  // default visualisation boolean (true or false)


        // input format is such that first two args are given without options
        if (args.length > 1) { // both file path and number of processors
            if (checkValidFileName(args[0])) {
                CliParsedInputs.filePathName = args[0]; // File path
            } else {
                //throw exception here?
                System.err.println("Invalid file path name");
                throw new IllegalArgumentException("Invalid file path name");
            }

            // number of processors
            if ( checkValidStringInt(args[1])) {
                CliParsedInputs.numberOfProcessors = Integer.parseInt(args[1]);
            } else {
                System.err.println("Invalid number of processors");
                throw new IllegalArgumentException("Invalid number of processors");
            }


        } else {
            // does not have minimum number of args.
            System.err.println("0 arguments");
            throw new IllegalArgumentException("0 arguments");
        }



        try {

            // detects all parsed arg options
            CommandLine commandLineParsed = parseOptionArguments(args);

            // option for parallisation with n number of cores
            if (commandLineParsed.hasOption("p")) {
                String numberOfCoresInput = commandLineParsed.getOptionValue("p");
                if (checkValidStringInt(numberOfCoresInput)) {
                    CliParsedInputs.numberOfCores = Integer.parseInt(numberOfCoresInput);
                }else{
                    //throw new IllegalArgumentException("invalid number of cores");
                }
            }

            // option for visualisation
            if (commandLineParsed.hasOption("v")) {
                String visualisationDisplayInput = commandLineParsed.getOptionValue("v");
                if (checkValidBoolean(visualisationDisplayInput )){
                    CliParsedInputs.visualisationDisplay = Boolean.parseBoolean(visualisationDisplayInput);
                } else{
                    //throw new IllegalArgumentException("invalid input for visualisation");
                }
            }

            // option for choosing an output file name
            if (commandLineParsed.hasOption("o")) {
                String outputFileNameInput = commandLineParsed.getOptionValue("o");
                if (checkValidFileName(outputFileNameInput)) {
                    CliParsedInputs.outputFileName = outputFileNameInput;
                }
            }


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }



    }


    public static boolean checkValidBoolean(String booleanString){
        return(booleanString.equalsIgnoreCase("true") || booleanString.equalsIgnoreCase("false"));
    }

    public static boolean checkValidFileName(String fileName){
        return (fileName.endsWith(".dot"));
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


    private CommandLine parseOptionArguments(String[] optionArguments) {
        Options cliOptions = createOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine line=null; //might change
        try {
            line = parser.parse(cliOptions, optionArguments); // args are the arguments passed to the  the application via the main method
            if (line.hasOption("output")){
                //do something
            } else if(line.hasOption("name")){
                // do something else
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        // too many args
        if (line.getArgList().size() > 2){
            throw new IllegalArgumentException("too many arguments");
        }
        return line;
    }



/*
 options to be included
−p N use N cores for execution in parallel (default is sequential )
−v visualise the search
−o OUPUT output file is named OUTPUT ( default is INPUT−output.dot)*/

    private Options createOptions(){

        Options createdOptions = new Options();

        Option numberOfCoresOption =    OptionBuilder.hasArgs(1)
                .withArgName("number of cores")
                .withDescription("Number of cores for execution in parallel")
                .create("p");

        Option visualisationOption = OptionBuilder.hasArgs(1)
                .withArgName("search visualisation")
                .withDescription("Visualisation of the search")
                .create("v");

        Option outputOption = OptionBuilder.hasArgs(1)
                .withArgName("output file name")
                .withDescription("The output file name")
                .create("o");


        createdOptions.addOption(numberOfCoresOption);
        createdOptions.addOption(visualisationOption);
        createdOptions.addOption(outputOption);


        return createdOptions;

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

    public int getNumberOfCores(){
        return numberOfCores;
    }







}
