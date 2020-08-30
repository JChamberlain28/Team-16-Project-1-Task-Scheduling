#Algorithms: Schedule Representation



## Schedule
Schedules are represented using `PartialSchedule` objects. These 
objects encode the information required to construct a partial 
scheduling of a set of tasks, such as when a task is scheduled to 
start and on what processor. These objects, in effect, are nodes in 
the solution space tree, and although an explicit tree structure is
 not implemented they do store a reference to the parent 
`PartialSchedule` instance that they are extending (scheduling an
additional task on top of). This allows for all parent/ancestor
schedules of a given instance to be iterated over easily, 
improving the ease with which the schedule can be converted 
into the output .dot file.

##Representation
The schedules described above are represented as follows:

```java
public class ScheduledTask {

    private int _processor;
    private int _startTime;
    private int _taskId;
}
```

The Partial schedules can be expanded using the expand method, which creates all possible
children of the current partial schedule and adds them to it.
