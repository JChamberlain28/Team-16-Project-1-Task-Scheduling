# Solution Space Pruning   

## Methods Used    

### Duplicate Detection   
The Algorithms developed have the possibility of producing the same schedule twice (e.g. a join in the solution space).
To limit the expansion of duplicate schedules in the final DFS Branch and Bound algorithm, a cache was used. This cache
stores the hashcode of the last 100,000 partial schedules. If a duplicate/equivalent schedule is encountered it is not
explored.

### Identical Tasks
Tasks that have identical weight and edges may cause extra child partial schedules with the only difference being one
 identical task being swapped for another. This increases the search space. To resolve this, a fixed order is enforced
 to ensure the algorithm only considers one of the identical tasks in each expansion. Thus, cutting down on child nodes 
 to explore.

### Processor Normalisation
To reduce the solution space, partial schedules that schedule the same set of tasks in the same way but, in a different CPU order are considered the same. This cuts down on the solution space significantly.

For example:   

| P1 | P2 |
|----|----|
|  A |  C |
|  B |    | 

is the same as

| P1 | P2 |
|----|----|
|  C |  A |
|    |  B |  




