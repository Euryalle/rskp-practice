// сумма массива из 10000 чисел с использованием forkjoin
// после каждой операции сложения добавить задержку в 1 мс при помощи Thread.sleep(1);

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class task1forkjoin {
    public static void main(String[] args) throws InterruptedException {
        int[] array = new int[10000];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(100);
        }
        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int sum = forkJoinPool.invoke(new Sum(array, 0, array.length));
        long end = System.currentTimeMillis();
        System.out.println("Сумма массива = " + sum);
        System.out.println("Время работы алгоритма = " + (end - start) + " ms");
    }

    static class Sum extends RecursiveTask<Integer> {
        private final int[] array;
        private final int from;
        private final int to;

        public Sum(int[] array, int from, int to) {
            this.array = array;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Integer compute() {
            if (to - from <= 2500) {
                int sum = 0;
                for (int i = from; i < to; i++) {
                    sum += array[i];
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return sum;
            } else {
                int mid = (from + to) / 2;
                Sum left = new Sum(array, from, mid);
                Sum right = new Sum(array, mid, to);
                left.fork();
                int rightResult = right.compute();
                int leftResult = left.join();
                return leftResult + rightResult;
            }
        }
    }
}