package input;

import org.apache.commons.cli.*;
import util.FilenameMethods;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * The CliParser class will parse the command line arguments and store the
 * required values and options inside an object. This object is then used throughout
 * the program to retrieve the required command line inputs as required.
 * CliParser class is implemented as a singleton class.
 * */
public class CliParser {

    // object that stored parsed command line inputs
    private static final CliParser _CliParsedInputs = new CliParser();

    // parsed inputs from the command line
    // The inputs filePathName and numberOfProcessors are required inputs.
    // The inputs numberOfCores, outputFileName and visualisationDisplay are optional inputs

    private String _fileName;
    private String _filePathName;

    private int _numberOfProcessors;
    private int _numberOfCores;

    private String _outputFileName;
    private String _outputFilePath;

    private boolean _visualisationDisplay;
    private boolean _successfulCliParse=true;

    // Detect and parse all command line arg options flags
    private CommandLine commandLineParsed;

    /*
     * Private Constructor preventing any other class from instantiating.
     */
    private CliParser() { }

    /*
     * Method called in the main class. This takes in the arguments provided in command line to parse.
     */
    public void UI(String[] args) {

        if (args.length == 0) {
            throw new IllegalArgumentException("Error: No arguments provided. " +
                    "Please enter a valid filename and number of processors in the format: \n" +
                    "java -jar <JAR NAME>.jar <INPUT FILE NAME> <NUMBER OF PROCESSORS> [-p N | -v | -o <OUTPUT FILE NAME>]\n" +
                    "For additional help enter the help option \"-h\" into the command line.");
        } else {
            // Detect and parse all command line arg options flags
            commandLineParsed = parseOptionArguments(args);
            if (commandLineParsed.hasOption("h")) {
                printHelpMessage();
                _CliParsedInputs._successfulCliParse=false;
                return;
            }
            // Parse the command line inputs and store them for use in the program.
            parseCli(args);

        }
    }

    // Method will parse and check the inputs are valid.
    // options are also  check for.
    private void parseCli(String[] args) {

        // input format is such that first two args are given without options
        // should include both file path and number of processors.
        if (args.length > 1) {
            // Checking valid input file name.
            if (FilenameMethods.checkValidDotFileExtension(args[0])) {
                // handles absolute file names
                File file = new File((args[0]));

                if (!file.exists()) {
                    throw new IllegalArgumentException("Error: file does not exist. Please enter an existing file name.\n" +
                            "For additional help enter the help option \"-h\" into the command line.");
                } else {
                    _CliParsedInputs._fileName = FilenameMethods.getFileName(args[0]); // File name
                    _CliParsedInputs._filePathName = args[0];
                }
            }
            // Checking valid number of processors.
            if (checkValidStringInt(args[1])) {
                _CliParsedInputs._numberOfProcessors = Integer.parseInt(args[1]);
            } else {
                throw new IllegalArgumentException("Error: Invalid number of processors. " +
                        "Please provide a valid number of processors \n" +
                        "For additional help enter the help option \"-h\" into the command line.");
            }
        } else {
            // does not have minimum number of args.
            throw new IllegalArgumentException("Error: Required arguments are missing." +
                    " Please enter a valid filename and number of processors in the format: \n" +
                    "java -jar <JAR NAME>.jar <INPUT FILE NAME> <NUMBER OF PROCESSORS> [-p N | -v | -o <OUTPUT FILE NAME>]\n" +
                    "For additional help enter the help option \"-h\" into the command line.");
        }

        // In the case that options are not included, default values are set.

        //default number of cores
        _CliParsedInputs._numberOfCores = 1;
        // default output file name
        String fileName = _CliParsedInputs._fileName;
        String defaultOutputFileName = (fileName.replaceAll(".dot$", "") + "-output.dot");
        _CliParsedInputs._outputFileName = defaultOutputFileName;
        // default output file path
        String defaultDir = FilenameMethods.getDirectoryOfJar();
        String defaultOutputFilePath = (defaultDir + File.separator + defaultOutputFileName);
        _CliParsedInputs._outputFilePath = defaultOutputFilePath;

        // default visualisation boolean (true or false)
        _CliParsedInputs._visualisationDisplay = false;

        // option flag provided for parallelization with n number of cores.
        if (commandLineParsed.hasOption("p")) {
            String numberOfCoresInput = commandLineParsed.getOptionValue("p");
            // Checking valid number of cores.
            if (numberOfCoresInput != null && checkValidStringInt(numberOfCoresInput)) {
                int numberOfCoresInt = Integer.parseInt(numberOfCoresInput);
                // warning if number of cores inputted(desired number of threads) larger than number of cores available.
                if (Runtime.getRuntime().availableProcessors() > numberOfCoresInt){
                    System.out.println("There are " +Runtime.getRuntime().availableProcessors()+ " available processors.");
                    System.out.println(numberOfCoresInt + " Threads will be created");
                }
                    _CliParsedInputs._numberOfCores = Integer.parseInt(numberOfCoresInput);
            } else {
                throw new IllegalArgumentException("Error: invalid number of cores." +
                        " Please enter a valid number of cores. \n" +
                        "For additional help enter the help option \"-h\" into the command line.");
            }
        }

        // option flag provided for visualisation.
        if (commandLineParsed.hasOption("v")) {
            _CliParsedInputs._visualisationDisplay = true;
        }

        // option flag provided for choosing an output file name.
        if (commandLineParsed.hasOption("o")) {
            String outputFileNameInput = commandLineParsed.getOptionValue("o");
            // Checking valid output file name.
            if (outputFileNameInput != null && FilenameMethods.checkValidDotFileExtension(outputFileNameInput)) {

                String noFolderFileName = FilenameMethods.getFileName(outputFileNameInput);
                _CliParsedInputs._outputFileName = noFolderFileName; // output File name
                _CliParsedInputs._outputFilePath = outputFileNameInput; // output file path

                    // check if output file path can be used to create a '.dot' file
                    String directoriesForOutput = _CliParsedInputs._outputFilePath.substring(0, _CliParsedInputs._outputFilePath.lastIndexOf(_CliParsedInputs._outputFileName));
                    try {
                        Files.createDirectories(Paths.get(directoriesForOutput));
                        new FileOutputStream(_CliParsedInputs._outputFilePath);
                        File outputFile = new File(_CliParsedInputs._outputFilePath);
                        if (!outputFile.exists()) {
                            throw new IllegalArgumentException("Error: File could not be created." +
                                    " Please enter a valid output file path.\n" +
                                    "For additional help enter the help option \"-h\" into the command line.");
                        }
                    } catch (IOException e) {
                        throw new IllegalArgumentException("Error: invalid output file path." + e.getMessage() +
                                " Please enter a valid output file path.\n" +
                                "For additional help enter the help option \"-h\" into the command line.");
                    }

            }
        }
    }



    /* Method to check if input visualisation boolean is valid.
     */
    public static boolean checkValidBoolean(String booleanString){
        return(booleanString.equalsIgnoreCase("true") || booleanString.equalsIgnoreCase("false"));
    }



    /* Method to check if input integer is valid.
     */
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

    /**
     * Parse the command line inputs for options added by the user.
     * @param optionArguments will take in arguments supplied by user into command line.
     * @return returns parsed commandline object.
     */
    private CommandLine parseOptionArguments(String[] optionArguments) {
        Options cliOptions = createOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine line=null;
        try {
            // args are the arguments passed to the the application via the main method
            line = parser.parse(cliOptions, optionArguments);
        } catch(Exception e) {
            throw new IllegalArgumentException( "Error: " + e.getMessage() +
                    ". Please enter valid options and valid arguments in the format \n" +
                    "java -jar <JAR NAME>.jar <INPUT FILE NAME> <NUMBER OF PROCESSORS> [-p N | -v | -o <OUTPUT FILE NAME>]\n" +
                    "For additional help enter the help option \"-h\" into the command line." );
        }

        // too many args have been provided
        if (line.getArgList().size() > 2 && !line.hasOption("h")){
            throw new IllegalArgumentException("Error: too many arguments. Please enter valid options and valid arguments in the format \n" +
                    "java -jar <JAR NAME>.jar <INPUT FILE NAME> <NUMBER OF PROCESSORS> [-p N | -v | -o <OUTPUT FILE NAME>]\n" +
                    "For additional help enter the help option \"-h\" into the command line.");
        }

        return line;
    }

    /**
     * Method to create the additional options that may be supplied to the command line.
     *
     * −p N use N cores for execution in parallel (default is sequential )
     * −v visualise the search
     * −o OUTPUT output file is named OUTPUT ( default is INPUT−output.dot)
     * -h option to print the help message
     * */
    private Options createOptions(){

        Options createdOptions = new Options();

        createdOptions.addOption("p", true, "Number of cores for execution in parallel");
        createdOptions.addOption("v", false, "Boolean for visualisation of the search");
        createdOptions.addOption("o", true, "The output file name");
        // additional option for helping user
        createdOptions.addOption("h", false, "user needs help message");
        return createdOptions;
    }


    /**
     * Prints a help message for user upon request with '-h' option flag.
     * Displays information regarding usage of program.
     **/
    private void printHelpMessage(){
        System.out.println("====================== HELP ======================\n" +
                "Usage Instructions\n" +
                "java -jar <JAR NAME>.jar <INPUT FILE NAME> <NUMBER OF PROCESSORS> [-p N | -v | -o <OUTPUT FILE NAME>]\n" +
                "\n" +
                "Required Parameters\n" +
                "INPUT FILE NAME: Name of .dot file representing the input graph " +
                "                 (must be in the same folder as the jar and have .dot extension)\n" +
                "NUMBER OF PROCESSORS: Number of processors / cores to schedule tasks onto\n" +
                "\nOptional Parameters\n" +
                "-p N: -p enables parallelisation (multithreading in algorithm), " +
                "       N specifies the number of cores to use for multithreading\n" +
                "-v: Enables the visualisation of the algorithm progress / actions\n" +
                "-o OUTPUT FILE NAME: Name of file the program should output (must include .dot extension). " +
                "                     Warning, supplying the name of an existing file will overwrite it.");
    }






    // Getter to return the CliParser object that contains the parsed command line inputs
    public static CliParser getCliParserInstance(){
        return _CliParsedInputs;
    }

    // Getters for the parsed command line inputs.
    public String getFilePathName() {
        return _filePathName;
    }
    public String getFileName() { return _fileName; } // new return

    public int getNumberOfProcessors() {
        return _numberOfProcessors;
    }
    public String getOutputFileName() {
        return _outputFileName;
    }
    public String getOutputFilePath() {
        return _outputFilePath;
    }
    public boolean isVisualisationDisplay() {
        return _visualisationDisplay;
    }
    public int getNumberOfCores(){
        return _numberOfCores;
    }

    // Getters for checking if cli parsing succeeded. Only continue program if successful.
    public boolean getSuccessfulCliParse(){
        return _successfulCliParse;
    }




}