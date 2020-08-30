# Heuristics

Both the A* and DFS B&B implementations utilise heuristics in order to improve the efficiency 
of their search through obtaining a lower-bound estimate for the finish time of a partial
schedule. The heuristics used in our implementation are composed of 3 primary measures:
Idle Time, Data Ready Time, and Bottom Level.

## Idle time
Idle Time refers to the cumulative amount of time that processors are sitting 'idle' (with
no task currently executing) during the execution of a schedule.

## Data Ready Time
Data Ready Time is a heuristic which encodes the latest earliest-possible start time for all tasks which can
be scheduled next in a schedule. In other words, for all tasks that can be scheduled at the current step,
we look at all their dependencies to find the earliest possible start time for that task, and then take
the maximum of these values.

## Bottom Level
The Bottom Level of a task is the longest path from that task to a 'leaf' task, i.e. a task
which is not the dependency of any other task. This gives a useful heuristic as it is optimistic given that
it ignores processor communication delays, but is also a strict lower bound for the finish time a schedule
since every task in this critical path must be scheduled at some point.
