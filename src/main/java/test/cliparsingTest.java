package test;




import static org.junit.Assert.assertEquals;

import input.CliParser;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;



public class cliparsingTest {

/*
*
*       only these two matter
*     private String filePathName;
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

        assertEquals("digraph2.dot", cliParser.getFilePathName());
        assertEquals(2, cliParser.getNumberOfProcessors());
        assertEquals("output.dot",cliParser.getOutputFileName());
    }


    @Test
    public void testInvalidFileName() {
        try {
            String[] argsInput = {"digraph2.dot2", "2"};


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



}
