// сумма массива из 10000 чисел с использованием потоков
// после каждой операции сложения добавить задержку в 1 мс при помощи Thread.sleep(1);

import java.util.Random;

public class task1threads {
    public static void main(String[] args) throws InterruptedException {
        int[] array = new int[10000];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(100);
        }
        long start = System.currentTimeMillis();
        int sum = 0;
        int[] sumArray = new int[4];
        Thread[] threads = new Thread[4];
        for (int i = 0; i < threads.length; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                for (int j = finalI * 2500; j < (finalI + 1) * 2500; j++) {
                    sumArray[finalI] += array[j];
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        for (int i = 0; i < sumArray.length; i++) {
            sum += sumArray[i];
            Thread.sleep(1);
        }
        long end = System.currentTimeMillis();
        System.out.println("Сумма массива = " + sum);
        System.out.println("Время работы алгоритма = " + (end - start) + " ms");
    }
}
