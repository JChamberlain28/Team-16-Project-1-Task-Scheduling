package unit;

import algorithm.PartialSchedule;
import algorithm.ScheduledTask;
import graph.Graph;
import graph.Vertex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        Assert.assertEquals(0, nullSchedule.getFinishTime());

    }

    @Test
    public void nullScheduleHasNoScheduledTask() {

        PartialSchedule nullSchedule = new PartialSchedule(dependencyGraph, 8);
        Assert.assertNull(nullSchedule.getScheduledTask());

    }

    @Test
    public void extendProducesNewPartialSchedules() {

        PartialSchedule nullSchedule = new PartialSchedule(dependencyGraph, 8);
        Assert.assertNotEquals(nullSchedule.extend().size(),0);

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

        Assert.assertNotEquals(nullSchedule.extend().size(), 0);

    }

    @Test
    public void extendBenchmark() {
        PartialSchedule nullSchedule = new PartialSchedule(dependencyGraph, 8);
        int step = 0;
        while (step++ < 10000) { nullSchedule.extend(); }
    }

    @Test
    public void extendPrunesDuplicatesForOneTask() {
        PartialSchedule nullSchedule = new PartialSchedule(dependencyGraph, 8);
        // Duplicate pruning should mean only one child schedule is produced
        Assert.assertEquals(1, nullSchedule.extend().size());
    }

    @Test
    public void extendPrunesDuplicatesForMultipleTasks() {
        PartialSchedule schedule = new PartialSchedule(dependencyGraph, 8);
        schedule = schedule.extend().get(0);  // get only child schedule
        // Duplicate pruning should mean only four child schedules are produced
        Assert.assertEquals(4, schedule.extend().size());
    }

    // Assumes parent schedule is valid
    private void assertValidSchedule(PartialSchedule parent, PartialSchedule schedule) {

        Vertex cV = schedule.getTask();  // Child vertex (task scheduled in 'schedule')

        int minStartTime = parent.getProcessorEndTimes()[schedule.getProcessor()];

        for (Vertex dependency : schedule.getTask().getIncomingVertices()) {
            ScheduledTask scheduledTask = schedule.getScheduledTask(dependency.getId());
            // If this is null then dependency has not been scheduled, which is not valid as a task can only be
            // scheduled after all of its parents (chronologically)
            Assert.assertNotNull(scheduledTask);
            if (scheduledTask.getProcessor() != schedule.getProcessor()) {
                minStartTime = Math.max(minStartTime, scheduledTask.getStartTime() +
                        dependency.getCost() + dependencyGraph.getEdgeWeight(dependency.getId(), cV.getId()));
            }
        }

        Assert.assertEquals(schedule.getStartTime(), minStartTime);

    }

    @Test
    public void allProducedSchedulesAreValid() {

        List<PartialSchedule> schedules = new ArrayList<PartialSchedule>();
        schedules.add(new PartialSchedule(dependencyGraph, 8));

        while (!schedules.isEmpty()) {
            PartialSchedule parent = schedules.remove(0);
            List<PartialSchedule> children = parent.extend();
            children.forEach(child -> assertValidSchedule(parent, child));
            schedules.addAll(children);
        }

    }

}
