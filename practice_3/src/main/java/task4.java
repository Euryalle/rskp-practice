//Реализовать следующую систему.
// Файл. Имеет следующие характеристики:
// Тип файла XML, JSON, XLS
// Размер файла — целочисленное значение от 10 до 100.
// Генератор файлов — генерирует файлы с задержкой от 100 до 1000 мс.
// Очередь — получает файлы из генератора.
// Вместимость очереди — 5 файлов.
// Обработчик файлов — получает файл из очереди. Каждый обработчик имеет параметр — тип файла, который он может обработать.
// Время обработки файла: «Размер файла*7мс». Система должна быть реализована при помощи инструментов RxJava.
// Вывод в формате: «Тип файла — размер файла — время обработки».

package rxjava.examples;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public class task4 {

    public static void main(String[] args) throws InterruptedException {
        Observable<File> fileObservable = Observable.interval(100, 1000, TimeUnit.MILLISECONDS)
                .map(aLong -> new File(aLong, (int) (Math.random() * 90 + 10), Type.values()[(int) (Math.random() * 3)]));

        Observable<File> fileObservable1 = fileObservable
                .filter(file -> file.getType() == Type.XML)
                .doOnNext(file -> System.out.println("XML " + file.getSize() + " " + file.getSize() * 7 + "ms"))
                .subscribeOn(Schedulers.io());

        Observable<File> fileObservable2 = fileObservable
                .filter(file -> file.getType() == Type.JSON)
                .doOnNext(file -> System.out.println("JSON " + file.getSize() + " " + file.getSize() * 7 + "ms"))
                .subscribeOn(Schedulers.io());

        Observable<File> fileObservable3 = fileObservable
                .filter(file -> file.getType() == Type.XLS)
                .doOnNext(file -> System.out.println("XLS " + file.getSize() + " " + file.getSize() * 7 + "ms"))
                .subscribeOn(Schedulers.io());

        Observable.merge(fileObservable1, fileObservable2, fileObservable3)
                .subscribe();

        Thread.sleep(10000);
    }

    private static class File {
        private long id;
        private int size;
        private Type type;

        public File(long id, int size, Type type) {
            this.id = id;
            this.size = size;
            this.type = type;
        }

        public long getId() {
            return id;
        }

        public int getSize() {
            return size;
        }

        public Type getType() {
            return type;
        }
    }

    private enum Type {
        XML, JSON, XLS
    }
}