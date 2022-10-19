package rxjava.examples;

import java.util.Random;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

//Преобразовать поток из 1000 случайных чисел от 0 до 1000 в поток, содержащий квадраты данных чисел.

//Ипользовать методы reaciveX

public class task2_1 {
    public static void main(String[] args) {
        Observable<Integer> observable = Observable.create(emitter -> {
            Random random = new Random();
            for (int i = 0; i < 1000; i++) {
                emitter.onNext(random.nextInt(1000));
            }
            emitter.onComplete();
        });
        observable.map(x -> x * x).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
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
