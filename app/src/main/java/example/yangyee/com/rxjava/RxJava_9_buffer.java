package example.yangyee.com.rxjava;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;

/**
 * author: Yangxusong
 * created on: 2019/6/27 0027
 */
public class RxJava_9_buffer {
    public static void main(String... arg) {
        window();
    }

    private static void buffer() {
        Observable.range(1, 10).buffer(3).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> mIntegers) throws Exception {
                System.out.println(mIntegers);
            }
        });
    }

    private static void groupBy() {
        Observable.range(1, 5).groupBy(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer mInteger) throws Exception {
                return mInteger % 2;
            }
        }).subscribe(new Consumer<GroupedObservable<Integer, Integer>>() {
            @Override
            public void accept(GroupedObservable<Integer, Integer> mIntegerIntegerGroupedObservable) throws Exception {
                if (mIntegerIntegerGroupedObservable.getKey() == 0) {
                    mIntegerIntegerGroupedObservable.subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer mInteger) {
                            System.out.println("双-" + mInteger);
                        }
                    });
                } else {
                    mIntegerIntegerGroupedObservable.subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer mInteger) {
                            System.out.println("单-" + mInteger);
                        }
                    });
                }
            }
        });
    }


    private static void scan() {
        Observable.range(1, 5).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer mInteger, Integer mInteger2) throws Exception {
                System.out.println("mInteger--" + mInteger + "--mInteger2--" + mInteger2);
                return (mInteger > mInteger2 ? mInteger : mInteger2);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mInteger) throws Exception {
                System.out.println(mInteger);
            }
        });
    }


    private static void window() {
        Observable.range(1, 10).window(3).subscribe(new Consumer<Observable<Integer>>() {
            @Override
            public void accept(Observable<Integer> mIntegerObservable) throws Exception {
                System.out.println(mIntegerObservable);
                mIntegerObservable.subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer mInteger) throws Exception {
                        System.out.println(mInteger);
                    }
                });
            }
        });
    }
}
