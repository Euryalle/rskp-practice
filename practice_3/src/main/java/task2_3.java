// Дан поток из 10 случайных чисел.
// Сформировать поток, содержащий все числа, кроме первых трех.

package rxjava.examples;

import java.util.Random;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class task2_3 {
    public static void main(String[] args) {
        Observable<Integer> numbers = Observable.create(emitter -> {
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                emitter.onNext(random.nextInt(100));
            }
            emitter.onComplete();
        });

        Observable<Integer> result = numbers.skip(3);

        result.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Integer s) {
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
