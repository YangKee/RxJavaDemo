package example.yangyee.com.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

/**
 * author: Yangxusong
 * created on: 2019/6/29 0029
 */
public class RxJava_12_Do {
    public static void main(String... arg) {
        doOnNext();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException mE) {
            mE.printStackTrace();
        }
    }

    private static void doOnNext() {
       Observable.create(new ObservableOnSubscribe<Long>() {
           @Override
           public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
               emitter.onNext(1L);
               emitter.onNext(2L);
               emitter.onError(new Throwable("Err"));
           }
       }).doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("doOnComplete");
            }
        }).doOnTerminate(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("doOnTerminate");
            }
        }).doAfterTerminate(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("doAfterTerminate");
            }
        }).doFinally(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("doFinally");
            }
        }).subscribe(new Observer<Long>() {
           @Override
           public void onSubscribe(Disposable d) {

           }

           @Override
           public void onNext(Long mLong) {
               System.out.println("accept-"+mLong);
           }

           @Override
           public void onError(Throwable e) {

           }

           @Override
           public void onComplete() {

           }
       });
    }
}
