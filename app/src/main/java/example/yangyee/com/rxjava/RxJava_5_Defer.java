package example.yangyee.com.rxjava;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;

/**
 * author: Yangxusong
 * created on: 2019/6/26 0026
 */
public class RxJava_5_Defer {

    public static void main(String... arn) {
        defer();
    }

    private static void defer() {

        Observable<String> defer = Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.just(System.currentTimeMillis()+"");
            }
        });

        defer.subscribe(new Consumer<String>() {
            @Override
            public void accept(String mS) throws Exception {
                System.out.println(mS);
            }
        });

        defer.subscribe(new Consumer<String>() {
            @Override
            public void accept(String mS) throws Exception {
                System.out.println(mS);
            }
        });
    }
}
