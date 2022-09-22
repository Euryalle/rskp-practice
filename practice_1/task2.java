// Пользователь вводит число, вывести его в квадрате. Запрос выполняется с задержкой в 1 - 5 секунд.
// В момент выполнения запроса пользователь имеет возможность отправить новый запрос.
// Необходимо реализовать через future.

import java.util.Random;
import java.util.concurrent.*;
import java.util.Scanner;

public class task2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите число: ");
            int number = scanner.nextInt();
            Future<Integer> future = executorService.submit(() -> {
                Random random = new Random();
                int delay = random.nextInt(5) + 1;
                Thread.sleep(delay * 1000);
                return number * number;
            });
            System.out.println("Результат: " + future.get());
        }
    }
}