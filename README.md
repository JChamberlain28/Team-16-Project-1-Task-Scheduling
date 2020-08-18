# Team: Saadboys   
# project-1: Task Scheduling
description of project
## Team members
| Name                        | UPI           | GitHub Name                                   |
| ----------------------------|--------------| ---------------------------------------------|
| Jack Chamberlain            | jcha928       | [JChamberlain28](http://github.com/JChamberlain28)  |
| Jackson Ayling-Campbell     | jayl164       | [loldabigboi](http://github.com/loldabigboi) |
| Daniel Hor                  | dhor051       | [Dhors](http://github.com/Dhors)   |
| Daniel Adams                | dada290       | [dagea](http://github.com/dagea)   |
| Saad Siddiqui               | msid633       | [Saad-Siddiqui27](http://github.com/Saad-Siddiqui27) |

## Compilation Instructions
Required system packages (Linux):
* gradle
* Java JDK 8 or higher
* git    

### Clone the Repository
Open terminal and run the following command   
`` git clone https://github.com/SoftEng306-2020/project-1-saadboys-16.git ``

Then navigate to the  repository   
`` cd project-1-saadboys-16 ``

### Compile/Build the Project   
Type the following to build the project and package it into a jar   
`` gradle fatJar ``

If the build fails, ensure java is in the classpath and the JAVA_HOME varialble is set to the home directory of your java installation.   

Navigate to the produced jar   
`` cd ./build/libs ``

Note the produced jar file is named "Project-1.0-SNAPSHOT-all.jar" by gradle.

## Usage Instructions
`` java -jar <JAR NAME>.jar <INPUT FILE NAME> <NUMBER OF PROCESSORS> [-p N | -v | -o <OUTPUT FILE NAME>] ``    
### Required Paramaters        
* INPUT FILE NAME: Name of .dot file representing the input graph (must be in the same folder as the jar and have .dot extension) 
* NUMBER OF PROCESSORS: Number of processors / cores to schedule tasks onto    
### Optional Paramaters    
* -p N: -p enables paralalisation (multithreading in algorithm), N specifies the number of cores to use for multithreading    
* -v: Enables the visualisation of the algorithm progress / actions
* -o <OUTPUT FILE NAME>: Name of file the program should output (must include .dot extension). Warning, supplying the name of an existing file will overwrite it.



## Helpful Links
