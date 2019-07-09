package example.yangyee.com.rxjava;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * author: Yangxusong
 * created on: 2019/6/27 0027
 */
public class RxJava_11_Merge {
    public static void main(String... arg) {
        join();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException mE) {
            mE.printStackTrace();
        }
    }


    private static void merge() {
        Observable<Long> range = Observable.interval(20, TimeUnit.MILLISECONDS);
        Observable<Long> range1 = Observable.interval(30, TimeUnit.MILLISECONDS);
        Observable.merge(range, range1).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long mInteger) throws Exception {
                System.out.println(mInteger);
            }
        });
    }


    private static void startWith() {
        Observable.range(1, 5).startWith(Arrays.asList(10, 11, 12)).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mInteger) throws Exception {
                System.out.println(mInteger);
            }
        });
    }

    private static void concat() {
        Observable<Long> range = Observable.intervalRange(1, 10, 0, 20, TimeUnit.MILLISECONDS);
        Observable<Long> range1 = Observable.intervalRange(10, 10, 0, 20, TimeUnit.MILLISECONDS);
        Observable.concat(range, range1).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long mInteger) throws Exception {
                System.out.println(mInteger);
            }
        });
    }

    private static void zip() {
        Observable<Long> range = Observable.intervalRange(1, 5, 0, 20, TimeUnit.MILLISECONDS);
        Observable<Long> range1 = Observable.intervalRange(10, 10, 0, 200, TimeUnit.MILLISECONDS);
        Observable.zip(range, range1, new BiFunction<Long, Long, String>() {
            @Override
            public String apply(Long mLong, Long mLong2) throws Exception {
                return String.format("分别是%d-%d", mLong, mLong2);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String mS) throws Exception {
                System.out.println(mS);
            }
        });
    }

    private static void combineLatest() {
        Observable<Long> range = Observable.intervalRange(1, 10, 0, 20, TimeUnit.MILLISECONDS);
        Observable<Long> range1 = Observable.intervalRange(10, 10, 0, 200, TimeUnit.MILLISECONDS);
        Observable.combineLatest(range, range1, new BiFunction<Long, Long, String>() {
            @Override
            public String apply(Long mLong, Long mLong2) throws Exception {
                return String.format("分别是%d-%d", mLong, mLong2);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String mS) throws Exception {
                System.out.println(mS);
            }
        });
    }

    private static void join() {
        Observable<Long> range = Observable.intervalRange(1, 10, 0, 30, TimeUnit.MILLISECONDS);
        Observable<Long> range1 = Observable.intervalRange(10, 10, 0, 10, TimeUnit.MILLISECONDS);
        range1.join(range, new Function<Long, ObservableSource<Long>>() {
            @Override
            public ObservableSource<Long> apply(Long mLong) throws Exception {
                return Observable.timer(20, TimeUnit.MILLISECONDS);
            }
        }, new Function<Long, ObservableSource<Long>>() {
            @Override
            public ObservableSource<Long> apply(Long mLong) throws Exception {
                return Observable.timer(20, TimeUnit.MILLISECONDS);
            }
        }, new BiFunction<Long, Long, String>() {
            @Override
            public String apply(Long mLong, Long mLong2) throws Exception {
                return String.format("分别是%d-%d", mLong, mLong2);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String mS) throws Exception {
                System.out.println(mS);
            }
        });
    }

}
