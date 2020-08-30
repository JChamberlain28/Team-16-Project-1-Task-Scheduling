# Algorithm

This section documents the details of the design and Implementation of the algorithm used in the project.

We designed and implemented two algorithms(A* & branch and bound) for the given problem as both algorithms had their own advantages and disadvantages that may be exploited in different scenarios.
Branch and bound being the slower algorithm was parallelized to improve the program's performance. Whereas the A* was used as it was already a fast algorithm, however, it used a huge amount of memory
The design of our program allows us to change the algorithm that is being used according to the user's specification.

## Subsections
* [Ideas and Approaches](ideas-and-Approaches.md)
* [Schedule Representation](Schedule-Representation.md)
* [Pruning Techniques ](Pruning.md)

   