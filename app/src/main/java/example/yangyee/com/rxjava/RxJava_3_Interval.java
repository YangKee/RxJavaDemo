package example.yangyee.com.rxjava;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * author: Yangxusong
 * created on: 2019/6/26 0026
 */
public class RxJava_3_Interval {
    public static void main(String... arn) {
//        System.out.println(Thread.currentThread().getName()+"--init-");
        interval();
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException mE) {
            mE.printStackTrace();
        }
    }

    private static void interval() {
        Observable.interval(200, TimeUnit.MILLISECONDS)
                .take(10).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long mLong) throws Exception {
                   System.out.println(Thread.currentThread().getName()+"---"+mLong);
            }
        });
    }
}
