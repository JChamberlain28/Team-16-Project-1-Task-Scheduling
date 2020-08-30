#Input Parsing Module

The input passed into the input-parsing module is a ``.dot`` file of a directed graph representing tasks with their time costs and dependencies. The module reads the ``.dot`` file and stores the data by constructing a ``graph`` object that is used by the algorithm to find an optimal schedule.

##Graph representation
The ``graph`` object stores a list of ``vertex`` objects that represent the vertices from the ``dot `` file. Each ``vertex`` object has an id, weight cost and stores a list of incomming and a list of outgoing vertices. The list of incomming and outgoing edges store integers corresponding the the communication cost of switch between proccessors for these tasks.
