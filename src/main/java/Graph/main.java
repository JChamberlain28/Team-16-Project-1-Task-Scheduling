package Graph;

import org.graphstream.graph.*;
import java.nio.file.*;;

public class main {

    public static void main(String[] args) throws Exception {

        String data = readFileAsString("C:\\Users\\a_sid\\IdeaProjects\\test2\\src\\digraph2.dot");
        System.out.println(data);




    }

    public static String readFileAsString(String fileName)throws Exception
    {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;



    }

}
