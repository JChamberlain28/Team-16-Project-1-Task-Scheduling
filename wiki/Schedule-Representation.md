#Algorithms: Schedule Representation



## Schedule
Schedules are represented using ```PartialSchedule``` objects. These 
objects encode the information required to construct a partial 
scheduling of a set of tasks, such as when a task is scheduled to 
start and on what processor. These objects, in effect, are nodes in 
the solution space tree. Schedules contain a set of ```ScheduledTask``` objects representing each task currently scheduled.


The Partial schedules can be expanded using the extend() method, which creates all possible
children of the current partial schedule.


## Representation of ScheduledTask
The ```ScheduledTask``` object described above are represented as follows:

```java
public class ScheduledTask {

    private int _processor;
    private int _startTime;
    private int _taskId;
}
```

