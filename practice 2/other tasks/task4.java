// Use WatchService to monitor a directory for changes to files.
// 1 - if you create new file in the directory, it will print the name of the file
// 2 - if you change the file, it will print all the changes in the file (add, delete, change) by comparing two arrays
// 3 - if you delete the file, it show its size and sum using the same algorithm as in task3

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.util.*;

public class task4 {
    public static void main(String[] args) throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get("D:\\aaa\\definition of a goblin\\other tasks\\");
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);
        while (true) {
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    System.out.println("Created: " + event.context().toString());
                }
                if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                    System.out.println("Modified: " + event.context().toString());
                }
                if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                    System.out.println("Deleted: " + event.context().toString());
                    Path path1 = Paths
                            .get("D:\\aaa\\definition of a goblin\\other tasks\\" + event.context().toString());
                    FileChannel fileChannel = FileChannel.open(path1);
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
                    System.out.println("Size: " + fileChannel.size());
                    System.out.println("Sum: " + sum);
                    fileChannel.close();
                }
            }
            key.reset();
        }
    }
}
