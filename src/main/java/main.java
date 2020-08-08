
        import input.InputParser;

        import java.nio.file.*;

public class main {

    public static void main(String[] args) throws Exception {

        InputParser.readInput();


    }

    public static String readFileAsString(String fileName)throws Exception
    {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;



    }

}
