package unit;

import algorithm.BruteForceAlgorithm;
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
        Vertex.resetIdCount();

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

    /*
    **Testing a schedule is produced, from input
     */
    @Test
    public void producesSchedule() {

        DfsBranchAndBound algo = new DfsBranchAndBound(dependencyGraph, 8, 1);
        PartialSchedule schedule = algo.findOptimalSchedule();
        Assert.assertNotNull(schedule);

    }

    /*
     **Testing an optimal schedule is produced, from input
     */
    @Test
    public void producesOptimalSchedule() {

        // Assumes brute force algorithm is correct
        int optimalEndTime = (new BruteForceAlgorithm(dependencyGraph, 8))
                .findOptimalSchedule()
                .getFinishTime();
        Assert.assertEquals(optimalEndTime, (new DfsBranchAndBound(dependencyGraph, 8, 1)
                .findOptimalSchedule()
                .getFinishTime()));

    }
}
