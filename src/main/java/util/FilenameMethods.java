package util;

import input.CliParser;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class FilenameMethods {


    /**
     * Gets the directory of the executable jar file
     * @return Directory of the jar file as a string
     */
    public static String getDirectoryOfJar(){

        // get directory of jar
        CodeSource codeSource = FilenameMethods.class.getProtectionDomain().getCodeSource();
        File runnableJar = null;

        try {
            runnableJar = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return runnableJar.getParentFile().getPath();

    }


    /* Method to check if input filename is valid absolute or relative path.
     */
    public static boolean checkIfAbsolutePath(String fileName){
        File file = new File(fileName);
        if (file.isAbsolute()) {
            return true;
        } else {
            return false;
        }
    }

    // remove the folder part of name
    public static String getFileName(String pathName) {

        if (pathName.contains("/") || pathName.contains("\\")) { // This removes the folder part of the file path
            if (!System.getProperty("os.name").toLowerCase().startsWith("windows")) {
                // linux
                return pathName.substring(pathName.lastIndexOf('/') + 1);

            } else if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {


                return pathName.substring(pathName.lastIndexOf('\\') + 1);
            } else{
                return pathName;
            }
        } else {
            // just a regular file name
            return pathName;
        }

    }

    /* Method to check if input filename is valid.
     */
    public static boolean checkValidFileName(String fileName){

        if (!fileName.endsWith(".dot")) {
            throw new IllegalArgumentException("Error: Invalid file name. Please provide a valid file " +
                    "name with the full '.dot' extension included.");
        } else if ( (fileName.startsWith("\\")) ) {
            // windows input is a folder which is invalid
            throw new IllegalArgumentException("Error: Invalid file path. Please provide a valid file path.");

        } else if ( System.getProperty("os.name").toLowerCase().startsWith("windows") && (fileName.startsWith("/"))  ) {
            // windows input is a folder which is linux notation
            throw new IllegalArgumentException("Error: Invalid file path. Please provide a valid file path.");
        } else {
            return true;
        }


    }


}
