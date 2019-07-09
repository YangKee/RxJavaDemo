package example.yangyee.com.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableConverter;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Consumer;

/**
 * author: Yangxusong
 * created on: 2019/6/29 0029
 */
public class RxJava_97_Compose {
    public static void main(String... arg) {

        compose();
    }

    private static void compose() {
        Observable.just("18").compose(new ObservableTransformer<String, Student>() {
            @Override
            public ObservableSource<Student> apply(Observable<String> upstream) {
                return Observable.just(new Student("小明", 18));
            }
        }).subscribe(new Consumer<Student>() {
            @Override
            public void accept(Student mStudent) throws Exception {

            }
        });


        Observable.just("18").as(new ObservableConverter<String, Observable<Student>>() {
            @Override
            public Observable<Student> apply(Observable<String> upstream) {
                return Observable.just(new Student("小明", 18));
            }
        }).subscribe(new Consumer<Student>() {
            @Override
            public void accept(Student mStudent) throws Exception {

            }
        });

    }
}
