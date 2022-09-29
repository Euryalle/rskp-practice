import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;

// use java.nio to read and show the content of the txt file

public class task1 {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("wrld.txt");
        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}