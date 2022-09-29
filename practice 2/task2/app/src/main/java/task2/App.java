// copy cas_16.cas file using make 4 different methods:
// 1 - file input/output stream
// 2 - file channel
// 3 - apache commons io
// 4 - file class
// check the time of each method
package task2;

import java.io.*;
import java.nio.file.*;
import java.nio.channels.FileChannel;
import java.nio.charset.*;
import org.apache.commons.io.FileUtils;

public class App {
    public static void main(String[] args) throws IOException {
        // 1 - file input/output stream
        long startTime = System.nanoTime();
        FileInputStream in = new FileInputStream("D:\\aaa\\definition of a goblin\\task2\\cas_16.cas");
        FileOutputStream out = new FileOutputStream("D:\\aaa\\definition of a goblin\\task2\\cas_16_1.cas");
        int c;
        while ((c = in.read()) != -1) {
            out.write(c);
        }
        in.close();
        out.close();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("1 - file input/output stream: " + duration + " ns");

        // 2 - file channel
        startTime = System.nanoTime();
        FileChannel inChannel = new FileInputStream("D:\\aaa\\definition of a goblin\\task2\\cas_16.cas").getChannel();
        FileChannel outChannel = new FileOutputStream("D:\\aaa\\definition of a goblin\\task2\\cas_16_2.cas")
                .getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inChannel.close();
        outChannel.close();
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("2 - file channel: " + duration + " ns");

        // 3 - apache commons io
        startTime = System.nanoTime();
        File source = new File("D:\\aaa\\definition of a goblin\\task2\\cas_16.cas");
        File dest = new File("D:\\aaa\\definition of a goblin\\task2\\cas_16_3.cas");
        FileUtils.copyFile(source, dest);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("3 - apache commons io: " + duration + " ns");

        // 4 - file class
        startTime = System.nanoTime();
        File file = new File("D:\\aaa\\definition of a goblin\\task2\\cas_16.cas");
        File file2 = new File("D:\\aaa\\definition of a goblin\\task2\\cas_16_4.cas");
        file.renameTo(file2);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("4 - file class: " + duration + " ns");
    }
}
