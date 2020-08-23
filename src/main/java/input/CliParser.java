package input;

import graph.Graph;
import org.apache.commons.cli.*;
import util.FilenameMethods;

import java.io.BufferedReader;
import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

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
    private String _filePathName;
    private int _numberOfProcessors;
    private int _numberOfCores;
    private String _outputFileName;
    private boolean _visualisationDisplay;

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
                    "java -jar <JAR NAME>.jar <INPUT FILE NAME> <NUMBER OF PROCESSORS> [-p N | -v | -o <OUTPUT FILE NAME>]");
        }
        else {

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
            if (checkValidFileName(args[0])) {
                _CliParsedInputs._filePathName = args[0]; // File path
                String dir = FilenameMethods.getDirectoryOfJar();

                //File file = new File(dir + File.separator + _CliParsedInputs._filePathName);
                File file = new File("C:\\Users\\OEM\\IdeaProjects\\project-1-saadboys-16\\src\\main\\java\\input\\digraph2.dot");

                if (!file.exists()) {
                    throw new IllegalArgumentException("Error: file does not exist. Please enter an existing file name.");
                }

            } else {
                throw new IllegalArgumentException("Error: Invalid file path name. Please provide a valid file " +
                        "name with the full '.dot' extension included.");
            }

            // Checking valid number of processors.
            if ( checkValidStringInt(args[1])) {
                _CliParsedInputs._numberOfProcessors = Integer.parseInt(args[1]);
            } else {
                throw new IllegalArgumentException("Error: Invalid number of processors. " +
                        "Please provide a valid number of processors");
            }

        } else {
            // does not have minimum number of args.
            throw new IllegalArgumentException("Error: Required arguments are missing." +
                    " Please enter a valid filename and number of processors in the format: \n" +
                     "java -jar <JAR NAME>.jar <INPUT FILE NAME> <NUMBER OF PROCESSORS> [-p N | -v | -o <OUTPUT FILE NAME>]");
        }

        // In the case that options are not included, default values are set.

        //default number of cores
        _CliParsedInputs._numberOfCores = 1;
        // default output file name
        String fileName = _CliParsedInputs._filePathName;
        String defaultOutputFileName =  ( fileName.replaceAll(".dot$", "") +  "-output.dot" );
        _CliParsedInputs._outputFileName = defaultOutputFileName;
        // default visualisation boolean (true or false)
        _CliParsedInputs._visualisationDisplay = false;

            // Detect and parse all command line arg options flags
            CommandLine commandLineParsed = parseOptionArguments(args);

            // option flag provided for parallisation with n number of cores.
            if (commandLineParsed.hasOption("p")) {
                String numberOfCoresInput = commandLineParsed.getOptionValue("p");
                // Checking valid number of cores.
                if (numberOfCoresInput!=null && checkValidStringInt(numberOfCoresInput)) {
                    _CliParsedInputs._numberOfCores = Integer.parseInt(numberOfCoresInput);
                }else{
                    throw new IllegalArgumentException("Error: invalid number of cores." +
                            " Please enter a valid number of cores.");
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
                if (outputFileNameInput!=null && checkValidFileName(outputFileNameInput)) {
                    _CliParsedInputs._outputFileName = outputFileNameInput;
                } else{
                    throw new IllegalArgumentException("Error: invalid output file name. " +
                            "Please provide a valid file name with the full '.dot' extension included.");
                }
            }

    }

    /* Method to check if input visualisation boolean is valid.
     */
    public static boolean checkValidBoolean(String booleanString){
        return(booleanString.equalsIgnoreCase("true") || booleanString.equalsIgnoreCase("false"));
    }

    /* Method to check if input filename is valid.
     */
    public static boolean checkValidFileName(String fileName){
        return (fileName.endsWith(".dot"));
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
                    "java -jar <JAR NAME>.jar <INPUT FILE NAME> <NUMBER OF PROCESSORS> [-p N | -v | -o <OUTPUT FILE NAME>]");
        }

        // too many args have been provided
        if (line.getArgList().size() > 2){
            throw new IllegalArgumentException("Error: too many arguments. Please enter valid options and valid arguments in the format \n" +
                    "java -jar <JAR NAME>.jar <INPUT FILE NAME> <NUMBER OF PROCESSORS> [-p N | -v | -o <OUTPUT FILE NAME>]");
        }

        return line;
    }

    /*
     * Method to create the additional options that may be supplied to the command line.
     *
     * −p N use N cores for execution in parallel (default is sequential )
     * −v visualise the search
     * −o OUTPUT output file is named OUTPUT ( default is INPUT−output.dot)
     * */
    private Options createOptions(){

        Options createdOptions = new Options();

        createdOptions.addOption("p", true, "Number of cores for execution in parallel");
        createdOptions.addOption("v", false, "Boolean for visualisation of the search");
        createdOptions.addOption("o", true, "The output file name");

        return createdOptions;
    }

    // Getter to return the CliParser object that contains the parsed command line inputs
    public static CliParser getCliParserInstance(){
        return _CliParsedInputs;
    }

    // Getters for the parsed command line inputs.
    public String getFilePathName() {
        return _filePathName;
    }
    public int getNumberOfProcessors() {
        return _numberOfProcessors;
    }
    public String getOutputFileName() {
        return _outputFileName;
    }
    public boolean isVisualisationDisplay() {
        return _visualisationDisplay;
    }
    public int getNumberOfCores(){
        return _numberOfCores;
    }

}
