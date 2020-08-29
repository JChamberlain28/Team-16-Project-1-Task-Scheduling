# Performance Testing

Performance testing was conducted for A*, DFS Branch and Bound and DFS Branch and Bound Parallelised (using 8 threads). 4GB heap space was allocated to the JVM with the "-Xmx4G" flag. Times recorded are an average of 3 runs.

## Test System
- Intel Core i5-6500 3.3Ghz (4 core)
- 16GB DDR4 2133Mhz

## Results



| Graph                       | A* Time         | DFS Branch and Bound Time       |  DFS Branch and Bound Parallelised (8 threads) 
| ----------------------------|--------------| ---------------------------------------------| ---------------------------------------------|
|--------------||--------------||--------------||--------------||--------------|
| Nodes_11_OutTree          | 3.125s      | 1.31s | 1.232s |
| g2    | 0.252s    | 0.251ms | times |
| g3              | time      | time  | time |
| g4               | time      | time  | time |
| g5              | time       | time | time |


It was found that A* is faster on large graphs (larger than 11 nodes) but runs out of memory too quickly to be practical with 4GB heap space.
