# CLI PARSING

## Process
To run the ``jar`` file from the command line, the user can pass in their arguments and options in the following form.

``java -jar <jar name> INPUT.dot P [-p N | -v | -o <output file name>``

Where 'P' is the number of processors to be used and 'N' is the number of cores to be used.
The -v flag enables a visualisation of the algorithm progress. -o is used to specify an output file name.
-h is used to show the help text.

The Apache commons library was used for CLI Parsing.
