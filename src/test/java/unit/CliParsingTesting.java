package unit;


import static org.junit.Assert.assertEquals;

import input.CliParser;


import org.junit.BeforeClass;
import org.junit.Test;
import util.FilenameMethods;

import java.io.File;


public class CliParsingTesting {

/*
*
*   only these two matter
*   private String filePathName;
    private int numberOfProcessors;
    *
    *
    private int numberOfCores;
    private String outputFileName;
    private boolean visualisationDisplay;

*
*
* */



    @BeforeClass
    public static void testSetup() {

    }



    @Test
    public void testStandardInputs() {

        String inputDigraph ="."+ File.separator +
                "src" + File.separator + "test" + File.separator +
                "java" + File.separator + "TestFiles" + File.separator
                + "digraph2.dot";

        String[] argsInput = {inputDigraph, "2"};

        CliParser cliParser = CliParser.getCliParserInstance();
        cliParser.UI(argsInput);

        assertEquals("digraph2.dot", cliParser.getFileName());
        assertEquals(2, cliParser.getNumberOfProcessors());
        assertEquals("digraph2-output.dot",cliParser.getOutputFileName());

        // check Default values
        assertEquals(1, cliParser.getNumberOfCores() );


        assertEquals(inputDigraph, cliParser.getFilePathName() );

        String outputDir = FilenameMethods.getDirectoryOfJar();
        String outputFilePath = (outputDir + File.separator + "digraph2-output.dot");
        assertEquals(outputFilePath, cliParser.getOutputFilePath() );

        assertEquals(false, cliParser.isVisualisationDisplay() );

    }

    /*
     * Input name parsing
     *
     *
     * */
    @Test
    public void testInvalidFileName() {


        try {
            String[] argsInput = {"digraph2", "2"};


            CliParser cliParser = CliParser.getCliParserInstance();
            cliParser.UI(argsInput);

        } catch (IllegalArgumentException e){
            assertEquals("Error: Invalid file name. Please provide a" +
                    " valid file name with the full '.dot' extension included.", e.getMessage());
        }
    }


    @Test
    public void testInvalidNumberOfProcessors() {
        try {
            String[] argsInput = {"digraph2.dot", "-2"};

            CliParser cliParser = CliParser.getCliParserInstance();
            cliParser.UI(argsInput);

        } catch (IllegalArgumentException e){
            assertEquals("Error: Unrecognized option: -2. " +
                    "Please enter valid options and valid arguments in the format \n" +
                    "java -jar <JAR NAME>.jar <INPUT FILE NAME> <NUMBER OF PROCESSORS> " +
                    "[-p N | -v | -o <OUTPUT FILE NAME>]\n" +
                    "For additional help enter the help option \"-h\" into the command line.", e.getMessage());
        }
    }


    @Test
    public void testInvalidNumberOfProcessorsNotInt() {
        try {
            String[] argsInput = {"digraph2.dot", "t"};

            CliParser cliParser = CliParser.getCliParserInstance();
            cliParser.UI(argsInput);

        } catch (IllegalArgumentException e){
            assertEquals("Error: file does not exist. Please enter an existing file name.\n" +
                    "For additional help enter the help option \"-h\" into the command line.", e.getMessage());
        }
    }



    //0 inputs
    @Test
    public void testInvalidNumberOfInputs() {
        try {
            String[] argsInput = {""};

            CliParser cliParser = CliParser.getCliParserInstance();
            cliParser.UI(argsInput);

        } catch (IllegalArgumentException e){
            assertEquals("Error: Required arguments are missing. Please enter a valid filename" +
                    " and number of processors in the format: \n" +
                    "java -jar <JAR NAME>.jar <INPUT FILE NAME> <NUMBER OF PROCESSORS> " +
                    "[-p N | -v | -o <OUTPUT FILE NAME>]\n" +
                    "For additional help enter the help option \"-h\" into the command line.", e.getMessage());
        }
    }


    // OPTION testing


    @Test
    public void testVisualisationOptionFlag() {
        String inputDigraph ="."+ File.separator +
                "src" + File.separator + "test" + File.separator +
                "java" + File.separator + "TestFiles" + File.separator
                + "digraph2.dot";

        String[] argsInput = {inputDigraph, "2", "-v"};

        CliParser cliParser = CliParser.getCliParserInstance();
        cliParser.UI(argsInput);

        assertEquals(true, cliParser.isVisualisationDisplay() );

    }


    @Test
    public void testCoresOptionFlag() {
        String inputDigraph ="."+ File.separator +
                "src" + File.separator + "test" + File.separator +
                "java" + File.separator + "TestFiles" + File.separator
                + "digraph2.dot";
        String[] argsInput = {inputDigraph, "2", "-p", "4"};

        CliParser cliParser = CliParser.getCliParserInstance();
        cliParser.UI(argsInput);

        assertEquals(4, cliParser.getNumberOfCores() );

    }


    @Test
    public void testValidFileOutput() {
        String inputDigraph ="."+ File.separator +
                "src" + File.separator + "test" + File.separator +
                "java" + File.separator + "TestFiles" + File.separator
                + "digraph2.dot";
        String[] argsInput = {inputDigraph, "2","-o", "OutputOfDi.dot" };


        CliParser cliParser = CliParser.getCliParserInstance();
        cliParser.UI(argsInput);

        assertEquals("digraph2.dot", cliParser.getFileName());
        assertEquals(2, cliParser.getNumberOfProcessors());
        assertEquals("OutputOfDi.dot",cliParser.getOutputFileName());
    }

    @Test
    public void testValidFilePathOutput() {
        String inputDigraph ="."+ File.separator +
                "src" + File.separator + "test" + File.separator +
                "java" + File.separator + "TestFiles" + File.separator
                + "digraph2.dot";
        String outPutDigraphPath ="."+ File.separator +
                "folder1" + File.separator + "folder2" + File.separator +
                "OutputOfDi.dot";

        String[] argsInput = {inputDigraph, "2","-o", outPutDigraphPath };

        CliParser cliParser = CliParser.getCliParserInstance();
        cliParser.UI(argsInput);

        assertEquals("digraph2.dot", cliParser.getFileName());
        assertEquals(2, cliParser.getNumberOfProcessors());

        assertEquals("OutputOfDi.dot",cliParser.getOutputFileName());

        assertEquals(outPutDigraphPath ,cliParser.getOutputFilePath());
    }

  /* these tests are machine dependant, please ignore
   @Test
    public void testValidAbsolutePathOutputWindows() {
        String[] argsInput = {"digraph2.dot", "2","-o", "C:\\Users\\folder1\\OutputOfDi.dot" };

        CliParser cliParser = CliParser.getCliParserInstance();
        cliParser.UI(argsInput);

        assertEquals("OutputOfDi.dot",cliParser.getOutputFileName());
        assertEquals("C:\\Users\\folder1\\OutputOfDi.dot",cliParser.getOutputFilePath());
    }

    @Test
    public void testValidAbsolutePathOutputLinux() {
        String[] argsInput = {"digraph2.dot", "2","-o", "/nfs/home/dir/OutputOfDi.dot" };

        CliParser cliParser = CliParser.getCliParserInstance();
        cliParser.UI(argsInput);

        assertEquals("OutputOfDi.dot",cliParser.getOutputFileName());
        assertEquals("/nfs/home/dir/OutputOfDi.dot",cliParser.getOutputFilePath());
    }*/





    @Test
    public void testNestedInputAsPathInput() {
        try {
            String[] argsInput = {"folder1/digraph2.dot", "2"};

            CliParser cliParser = CliParser.getCliParserInstance();
            cliParser.UI(argsInput);

        } catch (IllegalArgumentException e){
            assertEquals("Error: file does not exist. Please enter an existing file name.\n" +
                    "For additional help enter the help option \"-h\" into the command line.", e.getMessage());
        }
    }












}