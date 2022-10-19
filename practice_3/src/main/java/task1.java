// use rx java to create temperature and co2 sensor
// normal temperature is 25 and co2 is 70
// sensor will gerenerate random numbers for each temperature and co2 every second
// print out the temperature and co2 every second
// if temperature is higher than 25 and co2 is higher than 70, then print ALARM!!!
// need to use Observer and Observable from reactiveX

package rxjava.examples;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class task1 {
    public static void main(String[] args) throws InterruptedException {
        Observable<Integer> temperature = Observable.interval(1, TimeUnit.SECONDS)
                .map(i -> new Random().nextInt(50));
        Observable<Integer> co2 = Observable.interval(1, TimeUnit.SECONDS)
                .map(i -> new Random().nextInt(100));

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Integer t) {
                System.out.println("Temperature: " + t);
                if (t > 25) {
                    System.out.println("ALARM!!!");
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };

        Observer<Integer> observer2 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Integer t) {
                System.out.println("CO2: " + t);
                if (t > 70) {
                    System.out.println("ALARM!!!");
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };

        temperature.subscribe(observer);
        co2.subscribe(observer2);
        Thread.sleep(10000);
    }
}
