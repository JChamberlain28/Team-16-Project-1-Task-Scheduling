package unit;

import algorithm.PartialSchedule;
import graph.Graph;
import graph.Vertex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartialScheduleTests {

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
    public void nullScheduleIsIncomplete() {

        PartialSchedule nullSchedule = new PartialSchedule(dependencyGraph, 8);
        Assert.assertFalse(nullSchedule.isComplete());

    }

    @Test
    public void nullScheduleFinishTimeOfZero() {

        PartialSchedule nullSchedule = new PartialSchedule(dependencyGraph, 8);
        Assert.assertEquals(nullSchedule.getFinishTime(), 0);

    }

    @Test
    public void nullScheduleHasNullParent() {

        PartialSchedule nullSchedule = new PartialSchedule(dependencyGraph, 8);
        Assert.assertNull(nullSchedule.getParent());

    }

    @Test
    public void extendProducesNewPartialSchedules() {

        PartialSchedule nullSchedule = new PartialSchedule(dependencyGraph, 8);
        Assert.assertFalse(nullSchedule.extend().size() == 0);

    }

    @Test
    public void extendingSchedulesEventuallyTerminates() {

        PartialSchedule nullSchedule = new PartialSchedule(dependencyGraph, 8);

        List<PartialSchedule> schedules = new ArrayList<PartialSchedule>();
        schedules.add(nullSchedule);
        int maxSteps = 10000;
        int step = 0;

        while (!schedules.isEmpty()) {
            if (step++ > maxSteps) {
                Assert.fail("Modelled solution space is far too large!");
            }
            schedules.addAll(schedules.remove(0).extend());
        }

        Assert.assertFalse(nullSchedule.extend().size() == 0);

    }

    @Test
    public void extendBenchmark() {
        PartialSchedule nullSchedule = new PartialSchedule(dependencyGraph, 8);
        int step = 0;
        while (step++ < 10000) { nullSchedule.extend(); }
    }


}
