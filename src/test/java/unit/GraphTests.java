package unit;

import algorithm.PartialSchedule;
import algorithm.ScheduledTask;
import graph.Graph;
import graph.Vertex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GraphTests {

    private Graph dependencyGraph;

    @Before
    public void initGraph() {

        dependencyGraph = new Graph("test");

        Vertex a = new Vertex("a", 2);
        Vertex b = new Vertex("b", 3);
        Vertex c = new Vertex("c", 3);
        Vertex d = new Vertex("d", 2);

        dependencyGraph.addVertex(a);
        dependencyGraph.addVertex(b);
        dependencyGraph.addVertex(c);
        dependencyGraph.addVertex(d);

        dependencyGraph.addEdge(a.getId(), b.getId(), 1);
        dependencyGraph.addEdge(a.getId(), c.getId(), 2);
        dependencyGraph.addEdge(b.getId(), d.getId(), 2);
        dependencyGraph.addEdge(c.getId(), d.getId(), 1);

    }

    @Test
    public void identDetection() {
        Graph graph = new Graph("test");

        Vertex a = new Vertex("a", 1);
        Vertex b = new Vertex("b", 1);
        Vertex c = new Vertex("c", 2);
        Vertex d = new Vertex("d", 2);
        Vertex e = new Vertex("e", 2);

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);


        graph.addEdge(a.getId(), c.getId(), 2);
        graph.addEdge(b.getId(), c.getId(), 2);
        graph.addEdge(c.getId(), e.getId(), 1);
        graph.addEdge(c.getId(), d.getId(), 1);

        List<HashSet<Integer>> identicalListControl = new ArrayList<HashSet<Integer>>();
        // check same len, then contains all
        HashSet<Integer> h1 = new HashSet<Integer>();
        HashSet<Integer> h2 = new HashSet<Integer>();
        h1.add(a.getId());
        h1.add(b.getId());
        h2.add(d.getId());
        h2.add(e.getId());

        identicalListControl.add(h1);
        identicalListControl.add(h2);

        List<HashSet<Integer>> actual = graph.identicalTaskVirtualEdgeBuild();

        Assert.assertEquals(identicalListControl.size(), actual.size());


        for (HashSet<Integer> intListC : identicalListControl){
            boolean failed = true;
            for (HashSet<Integer> intList: actual){
                if (intListC.size() == intList.size()){
                    if (intListC.containsAll(intList)){
                        failed = false;
                        break;
                    }
                }
            }
            Assert.assertFalse(failed);
        }





    }





    @Test
    public void noFalsePositiveIdentDetectionEdgeWeight() {
        Graph graph = new Graph("test");

        Vertex a = new Vertex("a", 1);
        Vertex b = new Vertex("b", 1);
        Vertex c = new Vertex("c", 2);
        Vertex d = new Vertex("d", 2);
        Vertex e = new Vertex("e", 2);


        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);


        graph.addEdge(a.getId(), c.getId(), 2);
        graph.addEdge(b.getId(), c.getId(), 2);
        graph.addEdge(c.getId(), e.getId(), 9);
        graph.addEdge(c.getId(), d.getId(), 1);

        List<HashSet<Integer>> identicalListControl = new ArrayList<HashSet<Integer>>();
        // check same len, then contains all
        HashSet<Integer> h1 = new HashSet<Integer>();
        h1.add(a.getId());
        h1.add(b.getId());


        identicalListControl.add(h1);


        List<HashSet<Integer>> actual = graph.identicalTaskVirtualEdgeBuild();




        for (HashSet<Integer> intListC : identicalListControl){
            boolean failed = true;
            for (HashSet<Integer> intList: actual){
                if (intListC.size() == intList.size()){
                    if (intListC.containsAll(intList)){
                        failed = false;
                        break;
                    }
                }
            }
            Assert.assertFalse(failed);
        }





    }


    @Test
    public void noFalsePositiveIdentDetectionVertWeight() {
        Graph graph = new Graph("test");

        Vertex a = new Vertex("a", 1);
        Vertex b = new Vertex("b", 1);
        Vertex c = new Vertex("c", 2);
        Vertex d = new Vertex("d", 69);
        Vertex e = new Vertex("e", 2);


        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);


        graph.addEdge(a.getId(), c.getId(), 2);
        graph.addEdge(b.getId(), c.getId(), 2);
        graph.addEdge(c.getId(), e.getId(), 1);
        graph.addEdge(c.getId(), d.getId(), 1);

        List<HashSet<Integer>> identicalListControl = new ArrayList<HashSet<Integer>>();
        // check same len, then contains all
        HashSet<Integer> h1 = new HashSet<Integer>();
        h1.add(a.getId());
        h1.add(b.getId());


        identicalListControl.add(h1);


        List<HashSet<Integer>> actual = graph.identicalTaskVirtualEdgeBuild();




        for (HashSet<Integer> intListC : identicalListControl){
            boolean failed = true;
            for (HashSet<Integer> intList: actual){
                if (intListC.size() == intList.size()){
                    if (intListC.containsAll(intList)){
                        failed = false;
                        break;
                    }
                }
            }
            Assert.assertFalse(failed);
        }





    }



}


