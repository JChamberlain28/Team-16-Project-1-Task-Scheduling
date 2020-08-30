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
        String[] argsInput = {"digraph2.dot", "2"};

        CliParser cliParser = CliParser.getCliParserInstance();
        cliParser.UI(argsInput);

        assertEquals("digraph2.dot", cliParser.getFileName());
        assertEquals(2, cliParser.getNumberOfProcessors());
        assertEquals("digraph2-output.dot",cliParser.getOutputFileName());

        // check Default values
        assertEquals(1, cliParser.getNumberOfCores() );

        String inputDir = FilenameMethods.getDirectoryOfJar();
        //String inputFilePath = (inputDir  + File.separator + "digraph2.dot");
        assertEquals("digraph2.dot", cliParser.getFilePathName() );

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
            assertEquals("Invalid file path name", e.getMessage());
        }
    }


    @Test
    public void testInvalidNumberOfProcessors() {
        try {
            String[] argsInput = {"digraph2.dot", "-2"};

            CliParser cliParser = CliParser.getCliParserInstance();
            cliParser.UI(argsInput);

        } catch (IllegalArgumentException e){
            assertEquals("Invalid number of processors", e.getMessage());
        }
    }


    @Test
    public void testInvalidNumberOfProcessorsNotInt() {
        try {
            String[] argsInput = {"digraph2.dot", "t"};

            CliParser cliParser = CliParser.getCliParserInstance();
            cliParser.UI(argsInput);

        } catch (IllegalArgumentException e){
            assertEquals("Invalid number of processors", e.getMessage());
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
            assertEquals("0 arguments", e.getMessage());
        }
    }


    // OPTION testing


    @Test
    public void testVisualisationOptionFlag() {
        String[] argsInput = {"digraph2.dot", "2", "-v"};

        CliParser cliParser = CliParser.getCliParserInstance();
        cliParser.UI(argsInput);

        assertEquals(true, cliParser.isVisualisationDisplay() );

    }


    @Test
    public void testCoresOptionFlag() {
        String[] argsInput = {"digraph2.dot", "2", "-p", "4"};

        CliParser cliParser = CliParser.getCliParserInstance();
        cliParser.UI(argsInput);

        assertEquals(4, cliParser.getNumberOfCores() );

    }


    @Test
    public void testValidFileOutput() {
        String[] argsInput = {"digraph2.dot", "2","-o", "OutputOfDi.dot" };


        CliParser cliParser = CliParser.getCliParserInstance();
        cliParser.UI(argsInput);

        assertEquals("digraph2.dot", cliParser.getFileName());
        assertEquals(2, cliParser.getNumberOfProcessors());
        assertEquals("OutputOfDi.dot",cliParser.getOutputFileName());
    }

    @Test
    public void testValidFilePathOutput() {
        String[] argsInput = {"digraph2.dot", "2","-o", "./folder1/folder2/OutputOfDi.dot" };

        CliParser cliParser = CliParser.getCliParserInstance();
        cliParser.UI(argsInput);

        assertEquals("digraph2.dot", cliParser.getFileName());
        assertEquals(2, cliParser.getNumberOfProcessors());

        assertEquals("OutputOfDi.dot",cliParser.getOutputFileName());
        String outputDir = FilenameMethods.getDirectoryOfJar();
        String outputFilePath = (outputDir + File.separator + "folder1/folder2/OutputOfDi.dot");
        assertEquals(outputFilePath,cliParser.getOutputFilePath());
    }

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
    }



    @Test
    public void testFolderInputAsPathInput() {
        try {
            String[] argsInput = {"/folder1/digraph2.dot", "2"};

            CliParser cliParser = CliParser.getCliParserInstance();
            cliParser.UI(argsInput);

        } catch (IllegalArgumentException e){
            assertEquals("Invalid number of processors", e.getMessage());
        }
    }

    @Test
    public void testNestedInputAsPathInput() {
        try {
            String[] argsInput = {"folder1/digraph2.dot", "2"};

            CliParser cliParser = CliParser.getCliParserInstance();
            cliParser.UI(argsInput);

        } catch (IllegalArgumentException e){
            assertEquals("Invalid number of processors", e.getMessage());
        }
    }












}