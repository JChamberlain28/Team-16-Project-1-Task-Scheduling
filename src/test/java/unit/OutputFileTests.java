package unit;

import graph.Graph;
import graph.Vertex;
import input.InputParser;
import org.junit.Test;
import output.OutputGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class OutputFileTests {





    @Test
    public void testValidInputFileParsed() {

           /* a [Weight=2];
    b [Weight=3];
    a -> b [Weight=1];
    c [Weight=3];
    a -> c [Weight=2];
    d [Weight=2];
    b -> d [Weight=2];
    c -> d [Weight=1];*/

        // make expected output
        List<String> expectedOutputDotFile = new ArrayList<String>();
        expectedOutputDotFile.add("digraph \"testOutput\" {");
        expectedOutputDotFile.add("\ta [ Weight=2, Start=0, Processor=0 ];");
        expectedOutputDotFile.add("\tb [ Weight=3, Start=0, Processor=0 ];");
        expectedOutputDotFile.add("\ta -> b [ Weight=1 ];");
        expectedOutputDotFile.add("\tc [ Weight=3, Start=0, Processor=0 ];");
        expectedOutputDotFile.add("\ta -> c [ Weight=2 ];");
        expectedOutputDotFile.add("\td [ Weight=2, Start=0, Processor=0 ];");
        expectedOutputDotFile.add("\tb -> d [ Weight=2 ];");
        expectedOutputDotFile.add("\tc -> d [ Weight=1 ];");
        expectedOutputDotFile.add("}");


        // make graph object to generate output file from
        Graph testGraph = new Graph("testGraph");

        Vertex vertexa = new Vertex("a", 2);
        Vertex vertexb = new Vertex("b", 3);
        Vertex vertexc = new Vertex("c", 3);
        Vertex vertexd = new Vertex("d", 2);
        testGraph.addVertex( vertexa);
        testGraph.addVertex( vertexb);
        testGraph.addVertex( vertexc);
        testGraph.addVertex( vertexd);

        testGraph.addEdge(vertexa.getId(),vertexb.getId() ,1 );
        testGraph.addEdge(vertexa.getId(),vertexc.getId() ,2 );
        testGraph.addEdge(vertexb.getId(),vertexd.getId() ,2 );
        testGraph.addEdge(vertexc.getId(),vertexd.getId() ,1 );

        // generate output
        try {
            OutputGenerator.generate(testGraph, "testOutput.dot", "." + File.separator+ "testOutput.dot" );
        } catch (IOException e) {
            fail("could not run generate method");
        }
        // check output file exists
        File testOutputFile = new File("." + File.separator+ "testOutput.dot");
        if (!testOutputFile.exists()) {
            fail("file not created");
        }

        // check each line of output
        BufferedReader bufferReader = null;
        int lineCounter = 0;
        try {
            bufferReader = new BufferedReader(new FileReader(testOutputFile));


        String line = bufferReader.readLine();

            while (line != null) {

                // end of file
                if (line.length() == 1 && line.contains("}")) {
                    break;
                }
                assertEquals( expectedOutputDotFile.get(lineCounter), line);
                lineCounter++;
                line = bufferReader.readLine();
            }




        } catch (FileNotFoundException e) {
            fail("could not read file");
        } catch (IOException e) {
            fail("could not read line");
        }

        // delete after test
        testOutputFile.delete();


    }









}
