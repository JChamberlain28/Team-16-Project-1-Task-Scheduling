package util;

import input.CliParser;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class FilenameMethods {


    public static String getDirectoryOfJar(){
        // get directory of jar
        CodeSource codeSource = FilenameMethods.class.getProtectionDomain().getCodeSource();
        File runnableJar = null;

        try {
            runnableJar = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String jarDir = runnableJar.getParentFile().getPath();
        return jarDir;

    }


}
