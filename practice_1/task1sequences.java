// sum of array with 10000 numbers 
// после каждой операции сложения добавить задержку в 1 мс при помощи Thread.sleep(1);

import java.util.Random;

public class task1sequences {
    public static void main(String[] args) throws InterruptedException {
        int[] array = new int[10000];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(100);
        }
        long start = System.currentTimeMillis();
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            Thread.sleep(1);
        }
        long end = System.currentTimeMillis();
        System.out.println("Сумма массива = " + sum);
        System.out.println("Время работы алгоритма = " + (end - start) + " ms");
    }
}