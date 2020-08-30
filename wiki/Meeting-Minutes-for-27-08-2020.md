* Brainstormed ways of reducing memory usage of A\*
* Eventually decided to just use DFS B&B, as A\* is too memory intensive
* Came up with idea to use a 'cache' of 'seen' schedules in DFS B&B, to mimic
the A\* CLOSE set.

Todo before next meeting:
* Implement caching into DFS B&B