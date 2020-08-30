# Visualisation 


![Current Visualisation](Vis_current.png)

The visualisation component, as seen above, displays the following:
* Charts of JVM CPU and Memory usage
* Chart displaying current best schedule found 
* Names of input and output files
* End time of current best schedule found
* Status of parallelisation (if on/off and how many threads allocated)
* Partial and complete schedules checked
* Status of program (running/stopped) and time elapsed

These elements were chosen collectively, as they best represent a wide range of information about the status of the scheduling process.

JavaFX was used for GUI implementation due to familiarity and ease of use. 

### Design Process
For more details on the visualisation design process, [click here](Visualisation-Design.md)