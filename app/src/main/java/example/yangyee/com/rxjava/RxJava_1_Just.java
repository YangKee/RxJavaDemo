package example.yangyee.com.rxjava;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author: Yangxusong
 * created on: 2019/6/26 0026
 */
public class RxJava_1_Just {
    public static void main(String... arn) {
        just();
    }


    public static void just() {
        Observable<String> just = Observable.just("1111", "2222", "3333");
        just .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String mS) {
                System.out.println(mS);
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
