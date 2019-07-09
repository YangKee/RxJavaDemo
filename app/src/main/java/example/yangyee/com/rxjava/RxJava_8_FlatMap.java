package example.yangyee.com.rxjava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * author: Yangxusong
 * created on: 2019/6/27 0027
 */
public class RxJava_8_FlatMap {
    public static void main(String... arg) {
        test();
    }


    private static void flatMap() {
        Observable.range(0, 10).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer mInteger) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + mInteger);
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String mS) throws Exception {
                System.out.println(mS);
            }
        });

    }


    /**
     * 无序
     */
    private static void test() {
        Observable.range(1,10).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });

        try {
            Thread.sleep(200);
        } catch (InterruptedException mE) {
            mE.printStackTrace();
        }
    }


    public static void flatMapIterable(){
        Observable.just(10).flatMapIterable(new Function<Integer, Iterable<String>>() {
            @Override
            public Iterable<String> apply(Integer mInteger) throws Exception {
                List<String> integers = new ArrayList<>();
                for (Integer integer = mInteger; integer > 0; integer--) {
                    integers.add(String.valueOf(integer));
                }
                return integers;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String mS) throws Exception {
                System.out.println(mS);
            }
        });
    }


}
