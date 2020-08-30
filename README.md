# Team 16: Saadboys   

# SOFTENG 306 Project 1: Task Scheduling
A Java application which facilitates the creation of optimal schedulings of tasks.

## Team Members
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
* Oracle Java JDK/JRE 8 ONLY (Incompatibilities with JavaFX have limited the java version to compile and run the jar. Apologies. Also do not use OpenJDK). Compile and run tested on linux with jre1.8.0_261
* git    

### Clone the Repository
Create a new project in IntelliJ with "Add from version control". Use the following link to clone:
```https://github.com/SoftEng306-2020/project-1-saadboys-16.git```

### Compile/Build the Project   
Before continuing please ensure the project and gradle java version is set to 1.8 (JDK 8).
To check these:
go to ```File -> Project Structure -> Project SDK -> 1.8```
go to ```View -> Tool Windows -> Gradle -> Spanner Icon -> Gradle Settings -> Gradle JVM -> 1.8


Type the following in the IntelliJ terminal and then press CTRL + ENTER (the command should be highlighted green)
`` gradle fatJar ``


Navigate to the produced jar.   
`` cd ./build/libs ``

Note the produced jar file is named "Project-1.0-SNAPSHOT-all.jar" by gradle.

## Usage Instructions
`` java -jar <JAR NAME>.jar <INPUT FILE NAME> <NUMBER OF PROCESSORS> [-p N | -v | -o <OUTPUT FILE NAME>] ``    
### Required Parameters        
* INPUT FILE NAME: Name of .dot file representing the input graph (must be in the same folder as the jar and have .dot extension) 
* NUMBER OF PROCESSORS: Number of processors / cores to schedule tasks onto    
### Optional Parameters    
* -p N: -p enables parallelisation (multithreading in algorithm), N specifies the number of cores to use for multithreading    
* -v: Enables the visualisation of the algorithm progress / actions
* -o OUTPUT FILE NAME: Name of file the program should output (must include .dot extension). Warning, supplying the name of an existing file will overwrite it.
### Help Option
* -h: Shows a help message


## Helpful Links
[Wiki](/wiki/Home.md)
