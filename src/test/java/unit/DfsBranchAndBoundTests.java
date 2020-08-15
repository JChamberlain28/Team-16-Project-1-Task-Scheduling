package unit;

import algorithm.DfsBranchAndBound;
import algorithm.PartialSchedule;
import graph.Graph;
import graph.Vertex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DfsBranchAndBoundTests {


    private Graph dependencyGraph;

    @Before
    public void initGraph() {

        dependencyGraph = new Graph("test");

        Vertex a = new Vertex("a", 2);
        Vertex b = new Vertex("b", 3);
        Vertex c = new Vertex("c", 3);
        Vertex d = new Vertex("d", 2);

        dependencyGraph.addVertex(a.getId(), a);
        dependencyGraph.addVertex(b.getId(), b);
        dependencyGraph.addVertex(c.getId(), c);
        dependencyGraph.addVertex(d.getId(), d);

        dependencyGraph.addEdge(a.getId(), b.getId(), 1);
        dependencyGraph.addEdge(a.getId(), c.getId(), 2);
        dependencyGraph.addEdge(b.getId(), d.getId(), 2);
        dependencyGraph.addEdge(c.getId(), d.getId(), 1);

    }

    @Test
    public void producesSchedule() {

        DfsBranchAndBound algo = new DfsBranchAndBound(dependencyGraph, 8);
        Assert.assertNotNull(algo.findOptimalSchedule());

    }


}
