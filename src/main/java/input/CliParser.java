package input;

import algorithm.UnoptimalAlgo;
import graph.Graph;
import output.OutputGenerator;

import java.io.*;
import java.net.URISyntaxException;
import java.security.CodeSource;


public class CliParser {



    public static void UI(String[] args) {
        if (args.length == 0) {
            System.err.println("No arguments provided");
        }
        else {
            System.out.println("------------ SOFTENG306 Project 1 ------------");
            System.out.println("-------------group16 - Saddboys  -------------");

            handleInput(args);
        }
    }

    private static void handleInput(String[] args) {

        // check for all the strings validity
        String[] result = parseCli(args); // result[0]: file path, result[1]: num. processors, result[2]: output file, result[3]: num. cores, result[4]: visualize

        if (result != null) {
            try {


                //main method here.
                // get directory of jar
                // wtf?
                CodeSource codeSource = CliParser.class.getProtectionDomain().getCodeSource();
                File runnableJar = null;

                try {
                    runnableJar = new File(codeSource.getLocation().toURI().getPath());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                String jarDir = runnableJar.getParentFile().getPath();


                //make graph
                Graph graph = InputParser.readInput(result[0], jarDir);

                //run algo
                // use 2nd parameter for algo
                // method to select whih algo
                UnoptimalAlgo ua = new UnoptimalAlgo();
                ua.computeSchedule(graph);

                //visualisation?
                // option for visualisation
                // method to enable or disable visualisation







                //option to add output fie name
                // need a method to create Output File Name.
                OutputGenerator.generate(graph, result[2], jarDir);



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    private static String[] parseCli(String[] args) {
        String[] result = new String[5];

        // method to make new output file name should be added
        result[2] = "output.dot"; //default output name
        result[3] = "1"; //default number of cores
        result[4] = "false";  // default visualisation boolean (true or false)

        // Mandatory options
        if (args.length > 1) { // If both file path and number of processors are entered
            if (args[0].endsWith(".dot")) {
                result[0] = args[0]; // File path
            } else {
                //throw exception here?
                System.err.println("Invalid file path name");
            }


            if (result != null && checkValidStringInt(args[1])) {
                result[1] = args[1]; // Number of processors
            } else if (result != null){
                System.err.println("Invalid number of processors");

            } else {
                //nothing here
            }
        } else {
            System.err.println("0 arguments");
        }

        return result;
    }



    public static boolean checkValidStringInt(String str) {
        try {
            if (Integer.parseInt(str) >= 1){
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

    }


}
