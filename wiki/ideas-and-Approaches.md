
# Algorithms: Ideas and Approaches


## Sequential Algorithm
* Sequentially order the tasks on one processor by preserving the dependency constraints
* Execution time will be very fast
* The Schedule created will is very unlikely to be optimal
* Use this approach for the milestone 1 due to time constraints 


## Branch and Bound
* As explained in the lectures B&B is an algorithm for discrete and
combinatorial optimisation problems. 
* The Algorithm starts at the root of the input graph and then does the
DFS. When a valid solution is found, the current upper bound of the graph
updates.  
* While searching if the path reaches the current upper bound, the search
is stops.
* The B&B algorithm is easily parallelised by creating multiple threads
and putting the different parts of the current schedule on them.  


## A*
* A* algorithm is much faster than branch & bound and is a best-first 
search algorithm.
* A priority-queue is maintained for this algorithm
* It is memory intensive as it stores all the generated nodes in memory.
* Parallelization could be a challenging task due to time constraints.

 

