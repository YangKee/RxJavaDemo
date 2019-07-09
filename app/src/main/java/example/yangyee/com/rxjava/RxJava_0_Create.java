package example.yangyee.com.rxjava;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author: Yangxusong
 * created on: 2019/6/26 0026
 */
public class RxJava_0_Create {
    public static void main(String... arn) {
        create();
        try {
            Thread.sleep(50 * 1000);
        } catch (InterruptedException mE) {
            mE.printStackTrace();
        }
    }

    public static void create() {
        //被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                //我是一个发射器，用来发射事件给观察者，
                // onComplete和onError有且只能有一个，都会结束，后面的事件都观察不到
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onComplete();
                if (emitter.isDisposed()) {

                } else {
                    emitter.onError(new Throwable("err"));
                }
            }
        });

        //观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(String mS) {
                System.out.println("onNext-" + mS);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        //绑定
        observable.subscribe(observer);
    }


    private static void flowable() {
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + "开始发射数据" + System.currentTimeMillis());
                        for (int i = 1; i <= 500; i++) {
                            System.out.println(threadName + "发射---->" + i + "--request-" + e.requested());
                            e.onNext(i);
                            try {
                                Thread.sleep(10);//每隔100毫秒发射一次数据
                            } catch (Exception ex) {
                                e.onError(ex);
                            }
                        }
                        System.out.println(threadName + "发射数据结束" + System.currentTimeMillis());
                        e.onComplete();
                    }
                }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);            //注意此处，暂时先这么设置
                    }

                    @Override
                    public void onNext(Integer integer) {

                        try {
                            if (integer < 15 || (integer > 100 && integer < 110)) {
                                Thread.sleep(100);
                            } else {
                            }
                        } catch (InterruptedException ignore) {
                        }

                        System.out.println(Thread.currentThread().getName() + "接收---------->" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println(Thread.currentThread().getName() + "接收----> 完成");
                    }
                });
    }
}
