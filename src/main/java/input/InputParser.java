package input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class InputParser {


    public static void readInput() {

        BufferedReader bufferReader = null;
        try {
            File file = new File("C:\\Users\\dh\\eclipse-workspace\\project-1-saadboys-16\\src\\main\\java\\input\\digraph2.dot");
            bufferReader = new BufferedReader(new FileReader(file));

            String line = bufferReader.readLine();


            while ((line = bufferReader.readLine()) != null) {

                System.out.println(line);

                if (line.substring(0, 1).equals("}")) {
                    break;
                }


                if (line.contains("->")) { // edges




                } else if (!line.contains("->")) { // nodes




                } else { //end of file
                    System.out.println("end of file or GG");

                }


                }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}








