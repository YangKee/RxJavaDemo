package example.yangyee.com.rxjava;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * author: Yangxusong
 * created on: 2019/6/26 0026
 */
public class RxJava_6_Timer {
    public static void main(String... arn) {
        timer();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException mE) {
            mE.printStackTrace();
        }
    }

    private static void timer() {
        Observable.timer(1000, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long mLong) throws Exception {
                System.out.println(mLong);
            }
        });
    }

}
