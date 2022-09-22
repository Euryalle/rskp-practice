// Реализовать многопоточную систему
// Файл. Имеет следующие характеристики:
// 0. Тип файла (например XML, JSON, XLS)
// 1. Размер файла — целочисленное значение от 10 до 100.
// Генератор файлов -- генерирует файлы с задержкой от 100 до 1000 мс.
// Очередь — получает файлы из генератора. Вместимость очереди — 5 файлов.
// Обработчик файлов — получает файл из очереди. Каждый обработчик имеет параметр — тип файла, который он может обработать. Время обработки файла: «Размер файла*7мс»
// Система должна удовлетворять следующими условиям:
// 0. Должна быть обеспечена потокобезопасность.
// 1. Работа генератора не должна зависеть от работы обработчиков, и наоборот.
// 2. Если нет задач, то потоки не должны быть активны.
// 3. Если нет задач, то потоки не должны блокировать другие потоки.
// 4. Должна быть сохранена целостность данных.

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class task3 {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<File> queue = new ArrayBlockingQueue<>(5);
        Thread generator = new Thread(() -> {
            Random random = new Random();
            while (true) {
                try {
                    Thread.sleep(random.nextInt(900) + 100);
                    queue.put(new File(random.nextInt(90) + 10, random.nextInt(3)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        generator.start();
        Thread handler1 = new Thread(() -> {
            while (true) {
                try {
                    File file = queue.take();
                    if (file.getType() == 0) {
                        Thread.sleep(file.getSize() * 7);
                        System.out.println("Обработан файл типа XML размером " + file.getSize());
                    } else {
                        queue.put(file);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        handler1.start();
        Thread handler2 = new Thread(() -> {
            while (true) {
                try {
                    File file = queue.take();
                    if (file.getType() == 1) {
                        Thread.sleep(file.getSize() * 7);
                        System.out.println("Обработан файл типа JSON размером " + file.getSize());
                    } else {
                        queue.put(file);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        handler2.start();
        Thread handler3 = new Thread(() -> {
            while (true) {
                try {
                    File file = queue.take();
                    if (file.getType() == 2) {
                        Thread.sleep(file.getSize() * 7);
                        System.out.println("Обработан файл типа XLS размером " + file.getSize());
                    } else {
                        queue.put(file);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        handler3.start();
    }

    static class File {
        private int size;
        private int type;

        public File(int size, int type) {
            this.size = size;
            this.type = type;
        }

        public int getSize() {
            return size;
        }

        public int getType() {
            return type;
        }
    }
}
