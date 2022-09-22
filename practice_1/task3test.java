import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class task3test {

    // Реализовать следующую
    // многопоточную систему.
    // Файл. Имеет следующие характеристики:0.

    // Тип файла (например XML, JSON, XLS)
    // 1. Размер файла — целочисленное значение от 10 до 100.
    // Генератор файлов -- генерирует файлы с задержкой от 100 до 1000 мс.
    // Очередь — получает файлы из генератора. Вместимость очереди — 5
    // файлов.
    // Обработчик файлов — получает файл из очереди. Каждый обработчик
    // имеет параметр — тип файла, который он может обработать. Время обработки
    // файла: «Размер файла*7мс»
    // Система должна удовлетворять следующими условиям:
    // 0. Должна быть обеспечена потокобезопасность.
    // 1. Работа генератора не должна зависеть от работы обработчиков, и
    // наоборот.
    // 2. Если нет задач, то потоки не должны быть активны.
    // 3. Если нет задач, то потоки не должны блокировать другие потоки.
    // 4. Должна быть сохранена целостность данных.

    public static void main(String[] args) {
        FileQueue queue = new FileQueue(5);

        Thread generator = new Thread(new Generator(queue, queue.getLock(), queue.notFull));
        Thread handler = new Thread(new Handler(queue, "JSON", queue.getLock(), queue.notEmpty));
        Thread handler2 = new Thread(new Handler(queue, "XML", queue.getLock(), queue.notEmpty));
        Thread handler3 = new Thread(new Handler(queue, "XLS", queue.getLock(), queue.notEmpty));
        generator.start();
        handler.start();
        handler2.start();
        handler3.start();
    }

    public static class Handler implements Runnable {
        private FileQueue queue;
        private String type;
        private Lock lock;
        private Condition condition;

        public Handler(FileQueue queue, String type, Lock lock, Condition condition) {
            this.queue = queue;
            this.type = type;
            this.lock = lock;
            this.condition = condition;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (queue.isEmpty()) {
                        condition.await();
                    }
                    File file = queue.poll();
                    if (file.getType().equals(type)) {
                        System.out.println("Processing file " + file.getName() + " of type " + file.getType()
                                + " with size " + file.getSize());
                        Thread.sleep(file.getSize() * 7);
                        System.out.println("File " + file.getName() + " of type " + file.getType() + " with size "
                                + file.getSize() + " processed");
                    } else {
                        queue.add(file);
                    }
                    condition.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static class File {
        private String type;
        private int size;
        private String name;

        public File(String type, int size) {
            this.type = type;
            this.size = size;
            this.name = "File" + (int) (Math.random() * 1000);
        }

        public String getType() {
            return type;
        }

        public int getSize() {
            return size;
        }

        public String getName() {
            return name;
        }

    }

    public static File GenerateRandomFile() {
        String[] types = { "XML", "JSON", "XLS" };
        int size = (int) (Math.random() * 90 + 10);
        String type = types[(int) (Math.random() * 3)];
        return new File(type, size);
    }

    public static class Generator implements Runnable {
        private FileQueue queue;
        private Lock lock;
        private Condition condition;

        public Generator(FileQueue queue, Lock lock, Condition condition) {
            this.queue = queue;
            this.lock = lock;
            this.condition = condition;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (queue.size() == queue.getCapacity()) {
                        condition.await();
                    }
                    File file = GenerateRandomFile();
                    queue.add(file);
                    System.out.println("File " + file.getName() + " of type " + file.getType() + " with size "
                            + file.getSize() + " generated");
                    condition.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                try {
                    Thread.sleep((int) (Math.random() * 900 + 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class FileQueue {
        private int capacity;
        private Queue<File> queue = new LinkedList<File>();
        private ReentrantLock lock = new ReentrantLock();
        private Condition notFull = lock.newCondition();
        private Condition notEmpty = lock.newCondition();

        public FileQueue(int capacity) {
            this.capacity = capacity;
        }

        public void add(File file) throws InterruptedException {
            lock.lock();
            try {
                while (queue.size() == capacity) {
                    notFull.await();
                }
                queue.add(file);
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        public File remove() throws InterruptedException {
            lock.lock();
            try {
                while (queue.size() == 0) {
                    notEmpty.await();
                }
                File file = queue.remove();
                notFull.signal();
                return file;
            } finally {
                lock.unlock();
            }
        }

        public int size() {
            return queue.size();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }

        public File poll() {
            return queue.poll();
        }

        public Condition getEmptyCondition() {
            return notEmpty;
        }

        public Condition getFullCondition() {
            return notFull;
        }

        public Lock getLock() {
            return lock;
        }

        public int getCapacity() {
            return capacity;
        }
    }
}
