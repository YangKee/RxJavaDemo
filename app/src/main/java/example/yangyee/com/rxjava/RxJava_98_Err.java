package example.yangyee.com.rxjava;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * author: Yangxusong
 * created on: 2019/6/28 0028
 */
public class RxJava_98_Err {
    public static void main(String... arg) {
        retry();
    }

    private static void retry() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new IOException("error"));
                emitter.onNext(3);
            }
        })
               .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> mThrowableObservable) throws Exception {
                return mThrowableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable mThrowable) throws Exception {
                        if ((mThrowable instanceof IOException)) {
                            return Observable.just(1);
                        }
                        return Observable.error(mThrowable);
                    }
                });
            }
        })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer mInteger) {
                        System.out.println(mInteger);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
