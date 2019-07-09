package example.yangyee.com.rxjava;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * author: Yangxusong
 * created on: 2019/6/28 0028
 */
public class RxJava_100_Schedulers {

    public static void main(String... arg) {
        scheduler();
        try {
            Thread.sleep(100);
        } catch (InterruptedException mE) {
            mE.printStackTrace();
        }
    }

    private static void scheduler() {
        Observable.just(new Student("小明", 18))
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .map(new Function<Student, String>() {
                    @Override
                    public String apply(Student mStudent) throws Exception {
                        System.out.println(Thread.currentThread().getName());
                        return mStudent.name;
                    }
                })
                .observeOn(Schedulers.newThread())
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String mS) throws Exception {
                        System.out.println(Thread.currentThread().getName());
                        return 100;
                    }
                })
                .observeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribeOn(Schedulers.computation())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mInteger) throws Exception {
                System.out.println(Thread.currentThread().getName());
            }
        });
    }

     private static void schedulerSubscribeOn() {
        Observable.just(new Student("小明", 18))
                .subscribeOn(Schedulers.io())
                .map(new Function<Student, String>() {
                    @Override
                    public String apply(Student mStudent) throws Exception {
                        System.out.println(Thread.currentThread().getName());
                        return mStudent.name;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String mS) throws Exception {
                        System.out.println(Thread.currentThread().getName());
                        return 100;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mInteger) throws Exception {
                System.out.println(Thread.currentThread().getName());
            }
        });
    }




}
