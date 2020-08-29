# Performance Testing

Performance testing was conducted for A*, DFS Branch and Bound and DFS Branch and Bound Parallelised (using 8 threads). 4GB heap space was allocated to the JVM with the "-Xmx4G" flag. Times recorded are an average of 3 runs.

## Test System
- Intel Core i5-6500 3.3Ghz (4 core)
- 16GB DDR4 2133Mhz

## Results


### Scheduling on 2 cores
| Graph                       | A* Time         | DFS Branch and Bound Time       |  DFS Branch and Bound Parallelised (8 threads) 
| ----------------------------|--------------| ---------------------------------------------| ---------------------------------------------|
| Nodes_11_OutTree          | 3.125s      | 1.31s | 1.232s |
| Nodes_10_Random    | 0.252s    | 0.251ms | 0.272s |
| Nodes_9_SeriesParallel              | 0.273s      | 0.258s  | 0.271s |
| Nodes_8_Random              | 0.189s      | 0.215s  | 0.23s |
| Nodes_7_OutTree              | 0.21s       | 0.233s | 0.238s |
| 13node-fork         | 1min 23s       | time | 27.8s |

### Scheduling on 8 cores
| Graph                       | A* Time         | DFS Branch and Bound Time       |  DFS Branch and Bound Parallelised (8 threads) 
| ----------------------------|--------------| ---------------------------------------------| ---------------------------------------------|
| Nodes_11_OutTree          | 0.47s      | 0.32s | 0.332s |
| Nodes_10_Random    | 0.295s    | 0.251ms | 0.27s |
| Nodes_9_SeriesParallel              | 0.444s      | 0.285s  | 0.242s |
| Nodes_8_Random               | 0.192s   | 0.22s  | 0.243s |
| Nodes_7_OutTree              | 0.211s       | 0.238s | 0.262s |
| 13node-fork             | time      | time | time |


It was found that A* is faster on large graphs (larger than 11 nodes) but runs out of memory too quickly to be practical with 4GB heap space. It was also found that the multithreaded algorithm had no benifit on small graphs due to the time spent creating threads. It was slightly slower than single threaded DFS Branch and Bound in most cases.
