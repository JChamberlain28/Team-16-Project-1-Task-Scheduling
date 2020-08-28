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


    public static String createFileName() {


        return null;
    }


}
