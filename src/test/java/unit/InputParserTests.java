package unit;

import graph.Graph;
import graph.Vertex;
import input.CliParser;
import input.InputParser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InputParserTests {

    @BeforeClass
    public static void testSetup() {

    }

    String inputDigraph ="."+ File.separator +
            "src" + File.separator + "test" + File.separator +
            "java" + File.separator + "TestFiles" + File.separator
            + "digraph2.dot";
    String inputDigraph2 = "."  + File.separator +
            "src" + File.separator + "test" + File.separator +
            "java" + File.separator + "TestFiles" + File.separator
            + "digraphInvalid2.dot";;

   /* a [Weight=2];
    b [Weight=3];
    a -> b [Weight=1];
    c [Weight=3];
    a -> c [Weight=2];
    d [Weight=2];
    b -> d [Weight=2];
    c -> d [Weight=1];*/


    /*
    **Test valid input file correctly parsed - compare each individual graph element with expected
     */
    @Test
    public void testValidInputFileParsed() {

            List<Vertex> expectedListVertex = new ArrayList<Vertex>();
            Vertex vertexa = new Vertex("a", 2);
            Vertex vertexb = new Vertex("b", 3);
            Vertex vertexc = new Vertex("c", 3);
            Vertex vertexd = new Vertex("d", 2);

            expectedListVertex.add(vertexa);
            expectedListVertex.add(vertexb);
            expectedListVertex.add(vertexc);
            expectedListVertex.add(vertexd);





            System.out.println(inputDigraph);

            Graph graphOutput = InputParser.readInput(inputDigraph);



            List<Vertex> vertexList  = graphOutput.getVertices();
            int index = 0;
            for (Vertex vertex: vertexList){
                assertEquals(expectedListVertex.get(index).getLabel() ,vertex.getLabel() );
                assertEquals(expectedListVertex.get(index).getCost() ,vertex.getCost() );
                index++;
            }




    }


    /*
    **Test program correctly throws error on invalid input file
     */
    @Test
    public void testInvalidInputFileParsed() {

        try {
            Graph graphOutput = InputParser.readInput(inputDigraph2);

        } catch (IllegalArgumentException e){
            assertEquals("Error: Input '.dot' file is not a valid graph. " +
                    "There is an invalid line in the dot file. The input dot file " +
                    "should be valid.", e.getMessage());
        }


    }





}
