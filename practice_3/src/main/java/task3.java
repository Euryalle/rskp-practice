// Реализовать класс UserFriend. Поля — int userId, friendId.
// Заполнить массив объектов UserFriend случайными данными.
// Реализовать функцию: Observable<UserFriend> getFriends(int userId), возвращающую поток объектов UserFriend, по заданному userId.
// (Для формирования потока из массива возможно использование функции Observable.fromArray(T[] arr)).
// Дан массив из случайных userId. Сформировать поток из этого массива.
// Преобразовать данный поток в поток объектов UserFriend. Обязательно получение UserFriend через функцию getFriends.



import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class task3 {

    public static void main(String[] args) {
        List<UserFriend> userFriends = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            userFriends.add(new UserFriend(random.nextInt(10), random.nextInt(10)));
        }
        System.out.println(userFriends);

        Integer[] userIds = new Integer[10];
        for (int i = 0; i < 10; i++) {
            userIds[i] = random.nextInt(10);
        }
        System.out.println(userIds);

        Observable.fromArray(userIds)
                .flatMap((Function<Integer, Observable<UserFriend>>) integer -> getFriends(integer, userFriends))
                .subscribe(System.out::println);
    }

    private static Observable<UserFriend> getFriends(int userId, List<UserFriend> userFriends) {
        return Observable.fromIterable(userFriends)
                .filter(userFriend -> userFriend.getUserId() == userId);
    }

    private static class UserFriend {
        private int userId;
        private int friendId;

        public UserFriend(int userId, int friendId) {
            this.userId = userId;
            this.friendId = friendId;
        }

        public int getUserId() {
            return userId;
        }

        public int getFriendId() {
            return friendId;
        }

        @Override
        public String toString() {
            return "UserFriend{" +
                    "userId=" + userId +
                    ", friendId=" + friendId +
                    '}';
        }
    }
}




