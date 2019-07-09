package example.yangyee.com.rxjava;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author: Yangxusong
 * created on: 2019/6/26 0026
 */
public class RxJava_2_From {
    public static void main(String... arn) {
        from();
    }

    private static void from() {

        List<String> strings1 = Arrays.asList("1", "2", "3", "4");
        List<String> strings2 = Arrays.asList("1", "2", "3", "4", "5");
        Observable.fromArray(strings1).subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> mStrings) throws Exception {
                System.out.println(mStrings);
            }
        });


        Observable.just(strings1).subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> mStrings) throws Exception {
                System.out.println(mStrings);
            }
        });


        Observable.fromArray(strings1, strings2).subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> mStrings) throws Exception {
                System.out.println(mStrings);
            }
        });


        Maybe.fromFuture(new Future<String>() {


            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public String get() throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public String get(long timeout, @NonNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        });


        Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {

            }
        }).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }


    private static void testArray() {
        List<String> strings1 = Arrays.asList("1", "2", "3", "4");
    }
}
