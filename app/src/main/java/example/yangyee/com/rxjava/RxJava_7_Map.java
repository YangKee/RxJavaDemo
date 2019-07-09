package example.yangyee.com.rxjava;


import io.reactivex.Observable;
import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * author: Yangxusong
 * created on: 2019/6/27 0027
 */
public class RxJava_7_Map {
    public static void main(String... arn) {
        map();
    }

    public static void lift(final String name) {
        Observable<String> nameObservable = Observable.just(name);
        Observable<Student> observable = nameObservable.lift(new ObservableOperator<Student, String>() {
            @Override
            public Observer<String> apply(final Observer<? super Student> observer) throws Exception {
                Observer<String> nameObserver = new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(String mS) {
                        observer.onNext(new Student(name, 18));
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onComplete() {}

                };
                return nameObserver;
            }
        });
    }


    private static void map() {
        Observable.just("小明", "小红").map(new Function<String, Student>() {
            @Override
            public Student apply(String mS) throws Exception {
                return new Student(mS, 18);
            }
        }).subscribe(new Consumer<Student>() {
            @Override
            public void accept(Student mStudent) throws Exception {
                System.out.println(mStudent);
            }
        });
    }

    private static void cast() {
//       Observable.just("小明","小红").cast(Student.class).subscribe(new Consumer<Student>() {
//           @Override
//           public void accept(Student mStudent) throws Exception {
//               System.out.println(mStudent);
//           }
//       });


        Observable.just(new Student("小明", 18)).cast(person.class).subscribe(new Consumer<person>() {
            @Override
            public void accept(person person) throws Exception {
                System.out.println(person);
            }
        });

        //
    }

}
