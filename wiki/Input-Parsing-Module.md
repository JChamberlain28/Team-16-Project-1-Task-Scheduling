# Input Parsing Module

a ``dot.`` file representing tasks with their weights and dependencies is passed into the input parsing module that converts it into a ``graph`` object to be used by our graph traversing algorithm of our program.

## Graph representation
The ``graph`` object maintains a list of ``vertex`` objects that represent tasks from the ``dot `` file. The vertex objects have their own ids, weights and keep a list of incoming and outgoing vertices from the current vertex.
the list of incoming and outgoing edges have weights associated with them that define the communication cost between processors for these tasks.

