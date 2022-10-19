// Даны два потока по 1000 элементов: первый содержит случайную букву, второй — случайную цифру.
// Сформировать поток, каждый элемент которого объединяет элементы из обоих потоков.
// Например, при входных потоках (A, B, C) и (1, 2, 3) выходной поток — (A1, B2, B3).

package rxjava.examples;

import java.util.Random;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class task2_2 {
    public static void main(String[] args) {
        Observable<String> letters = Observable.create(emitter -> {
            Random random = new Random();
            for (int i = 0; i < 1000; i++) {
                emitter.onNext(String.valueOf((char) (random.nextInt(26) + 'a')));
            }
            emitter.onComplete();
        });

        Observable<String> digits = Observable.create(emitter -> {
            Random random = new Random();
            for (int i = 0; i < 1000; i++) {
                emitter.onNext(String.valueOf(random.nextInt(10)));
            }
            emitter.onComplete();
        });

        Observable<String> result = Observable.zip(letters, digits, (letter, digit) -> letter + digit);

        result.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }
}
