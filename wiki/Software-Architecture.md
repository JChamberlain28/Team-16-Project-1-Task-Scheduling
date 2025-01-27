## Software Architecture

The overall architecture of our software consists of a selection of distinct modules which do not directly interact but instead are used in tandem by the main application scope. An exception is made for certain smaller modules, which are more for the purpose of providing a common 'language' for modules to communicate to the main application in, such as the Graph module.

## Implemented Modules
* [**Command Line Interface**](CLI-Parsing.md): this module is responsible for parsing the arguments passed to the program from the command line. It facilitates the passing of additional optional parameters, allowing finer tuning of the program's behaviour.
* [**Input Parser**](Input-Parsing-Module.md): this module's function is to parse the input .dot file specified by the user, which encodes the task dependency information. It converts this .dot file into a `Graph` object that contains Vertex instances for each task and stores a `HashMap` representation of the dependencies between tasks (edges).
* **Graph**: the `Graph` module handles the internal representation of the dependency information specified in the input .dot file. The classes within this module (`Graph` / `Vertex`) are used throughout the program's modules in order to provide a universal language for the transferal of relevant information.
* [**Algorithm**](Algorithm-Module.md): this module is the largest module of the program, and contains architectures for encoding task schedules, as well as the actual algorithms for computing optimal schedules. This task schedule encoding is the `PartialSchedule` class, which stores which tasks were allocated on which processor and for what start time. Currently, the algorithms implemented are DFS branch and bound, Brute force, and A*. The DFS branch and bound algorithm has also been parallelized.
* [**Output Generation**](Output.md): the Output Generation module is concerned with converting the schedule produced by the Algorithm module back into a .dot file, which is then saved to the user's file system.
* [**Visualisation**](Visualisation-Summary.md): this module utilises the JavaFX GUI library to create a visualisation of the program's progress, such as the best schedule found so far, memory usage, and time elapsed. This module interfaces directly with the Algorithm module in order to ensure live updates to its various components.

