# Output generation

The output generation module converts a ```PartialSchedule``` representation into an output ```.dot``` file.

It does this by iterating over the ScheduledTask array in the found optimal ```PartialSchedule``` object, using a BufferedWriter. The output does not necessarily maintain the input order of vertices; however, this was not identified as a requirement.

