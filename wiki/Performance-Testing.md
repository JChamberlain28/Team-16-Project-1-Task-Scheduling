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
| 13node-fork         | 29s       | 56s | 34.4s |


It was found that A* is faster on large graphs (larger than 11 nodes) but runs out of memory too quickly to be practical with 4GB heap space. It was also found that the multithreaded algorithm had no benifit on small graphs due to the time spent creating threads. It was slightly slower than single threaded DFS Branch and Bound in most cases.    



13node-fork is a graph not supplied and is provided below:
```
digraph "13node-fork" {
   
   	1	 [Weight=3];
   	2	 [Weight=5];
   	2 -> 1	 [Weight=16];
   	3	 [Weight=3];
   	3 -> 1	 [Weight=13];
   	4	 [Weight=2];
   	4 -> 1	 [Weight=13];
   	5	 [Weight=8];
   	5 -> 1	 [Weight=4];
   	6	 [Weight=5];
   	6 -> 1	 [Weight=9];
   	7	 [Weight=9];
   	7 -> 1	 [Weight=16];
   	8	 [Weight=8];
   	8 -> 1	 [Weight=13];
   	9	 [Weight=7];
   	9 -> 1	 [Weight=18];
   	10	 [Weight=2];
   	10 -> 1	 [Weight=18];
   	11	 [Weight=3];
   	11 -> 1	 [Weight=13];
   	12	 [Weight=8];
   	12 -> 1	 [Weight=13];
   	13	 [Weight=3];
   	13 -> 1	 [Weight=16];
   }
```
