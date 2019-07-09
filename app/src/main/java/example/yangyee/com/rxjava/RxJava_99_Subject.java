package example.yangyee.com.rxjava;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.UnicastSubject;

/**
 * author: Yangxusong
 * created on: 2019/6/28 0028
 */
public class RxJava_99_Subject {
    public static void main(String... arg) {
        unicastSubject();
    }

    private static void replaySubject() {
        ReplaySubject<Integer> replaySubject = ReplaySubject.createWithSize(2);
        replaySubject.onNext(1);
        replaySubject.onNext(2);
        replaySubject.onNext(3);

        replaySubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mInteger) throws Exception {
                System.out.println(mInteger);
            }
        });
        replaySubject.onNext(4);
        replaySubject.onNext(5);
    }

    private static void behaviorSubject() {
        BehaviorSubject<Integer> behaviorSubject = BehaviorSubject.create();
        behaviorSubject.onNext(1);
        behaviorSubject.onNext(2);
        behaviorSubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mInteger) throws Exception {
                System.out.println(mInteger);
            }
        });
        behaviorSubject.onNext(3);
    }

    private static void publishSubject() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        publishSubject.onNext(1);
        publishSubject.onNext(2);
        publishSubject.onNext(3);

        publishSubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mInteger) throws Exception {
                System.out.println(mInteger);
            }
        });
        publishSubject.onNext(4);
        publishSubject.onNext(5);
    }


    private static void asyncSubject() {
        AsyncSubject<Integer> integerAsyncSubject = AsyncSubject.create();
        integerAsyncSubject.onNext(1);
        integerAsyncSubject.onNext(2);
        integerAsyncSubject.onNext(3);

        integerAsyncSubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mInteger) throws Exception {
                System.out.println(mInteger);
            }
        });
        integerAsyncSubject.onNext(4);
        integerAsyncSubject.onNext(5);
//        integerAsyncSubject.onComplete();
        integerAsyncSubject.onNext(6);
    }


    private static void unicastSubject() {
        UnicastSubject<Integer> unicastSubject = UnicastSubject.create();
        unicastSubject.onNext(1);
        unicastSubject.onNext(2);

        unicastSubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mInteger) throws Exception {
                System.out.println("1--" + mInteger);
            }
        });
        unicastSubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mInteger) throws Exception {
                System.out.println("2--" + mInteger);
            }
        });
        unicastSubject.onNext(3);
    }
}
