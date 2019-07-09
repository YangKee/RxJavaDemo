package example.yangyee.com.rxjava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * author: Yangxusong
 * created on: 2019/6/27 0027
 */
public class RxJava_10_Fitter {
    public static void main(String... arg) {
        skip();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException mE) {
            mE.printStackTrace();
        }
    }

    private static void fitter() {
        Observable.range(1, 30).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer mInteger) throws Exception {
                return mInteger % 10 == 0;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mInteger) throws Exception {
                System.out.println(mInteger);
            }
        });
    }


    private static void element() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        Observable.fromIterable(list).elementAt(18).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mInteger) throws Exception {
                System.out.println(mInteger);
            }
        });
    }

    private static void distinct() {
        Observable.range(1, 10).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer mInteger) throws Exception {
                return mInteger % 5;
            }
        }).distinct().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mInteger) throws Exception {
                System.out.println(mInteger);
            }
        });


        //重复的条件
        Observable.just(new Student("小明", 19), new Student("小明", 10), new Student("小红", 18), new Student("小明", 10)).distinctUntilChanged(new Function<Student, String>() {
            @Override
            public String apply(Student mStudent) throws Exception {
                return mStudent.name;
            }
        }).subscribe(new Consumer<Student>() {
            @Override
            public void accept(Student mStudent) throws Exception {
                System.out.println(mStudent);
            }
        });
    }


    private static void skip() {

    }
    private static void take() {
        Observable.range(1, 10).takeWhile(new Predicate<Integer>() {
            @Override
            public boolean test(Integer mInteger) throws Exception {
                return mInteger < 3;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mInteger) throws Exception {
                System.out.println(mInteger);
            }
        });

    }

    private static void sample() {
        Observable.interval(100,TimeUnit.MILLISECONDS).sample(300,TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long mLong) throws Exception {
                System.out.println(mLong);
            }
        });
    }

    private static void ignoreElements() {
    }

    private static void throttleFirst() {
        Observable.interval(10, TimeUnit.MILLISECONDS).throttleFirst(30, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long mLong) throws Exception {
                System.out.println(mLong);
            }
        });
    }


    private static void debounce() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // send events with simulated time wait
                emitter.onNext(1); // skip
                Thread.sleep(400);
                emitter.onNext(2); // deliver
                Thread.sleep(505);
                emitter.onNext(3); // skip
                Thread.sleep(100);
                emitter.onNext(4); // deliver
                Thread.sleep(605);
                emitter.onNext(5); // deliver
                Thread.sleep(510);
                emitter.onComplete();
            }
        }).debounce(500, TimeUnit.MILLISECONDS).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mLong) throws Exception {
                System.out.println(mLong);
            }
        });
    }


}
