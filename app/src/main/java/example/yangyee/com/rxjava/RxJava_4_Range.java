package example.yangyee.com.rxjava;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * author: Yangxusong
 * created on: 2019/6/26 0026
 */
public class RxJava_4_Range {
    public static void main(String... arn) {
//        System.out.println(Thread.currentThread().getName()+"--init-");
        range();
//        try {
//            Thread.sleep(5*1000);
//        } catch (InterruptedException mE) {
//            mE.printStackTrace();
//        }
    }

    private static void range() {
        Observable.range(0, 100).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mInteger) throws Exception {
                System.out.println(Thread.currentThread().getName() + "---" + mInteger);
            }
        });
    }
}
