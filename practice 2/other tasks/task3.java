// реализовать функцию нахождения 16-битной контрольной суммы файла с использованием бинарных операций и bytebuffer

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.util.*;

public class task3 {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("D:\\aaa\\definition of a goblin\\other tasks\\wrld.txt");
        FileChannel fileChannel = FileChannel.open(path);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = fileChannel.read(buffer);
        int sum = 0;
        while (read != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                sum += buffer.get();
            }
            buffer.clear();
            read = fileChannel.read(buffer);
        }
        System.out.println(sum);
        fileChannel.close();
    }
}
