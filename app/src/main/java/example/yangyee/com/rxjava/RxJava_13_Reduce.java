package example.yangyee.com.rxjava;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * author: Yangxusong
 * created on: 2019/7/2 0002
 */
public class RxJava_13_Reduce {
    public static void main(String... arg) {
        toMap();
    }

    private static void reduce() {
        Observable.range(1, 10).reduce(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer mInteger, Integer mInteger2) throws Exception {
                System.out.println("mInteger--" + mInteger + "--mInteger2--" + mInteger2);
                return mInteger + mInteger2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mInteger) throws Exception {
                System.out.println(mInteger);
            }
        });


        Observable.range(1, 10).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer mInteger, Integer mInteger2) throws Exception {
                System.out.println("mInteger--" + mInteger + "--mInteger2--" + mInteger2);
                return mInteger + mInteger2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer mInteger) throws Exception {
                System.out.println(mInteger);
            }
        });
    }

    private static void reduceWith() {
        Observable.range(1, 10).reduceWith(new Callable<List<Integer>>() {
            @Override
            public List<Integer> call() throws Exception {
                return new ArrayList<>();
            }
        }, new BiFunction<List<Integer>, Integer, List<Integer>>() {
            @Override
            public List<Integer> apply(List<Integer> mIntegers, Integer mInteger) throws Exception {
                System.out.println("mIntegers--" + mIntegers + "--mInteger2--" + mInteger);
                mIntegers.add(mInteger);
                return mIntegers;
            }
        }).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> mInteger) throws Exception {
                System.out.println(mInteger);
            }
        });
    }


    private static void count() {
        Observable.range(1, 10).count().subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long mLong) throws Exception {

            }
        });
    }


    private static void collect() {
        Observable.just('c', 'a', 's', 's', 't', 'i','m','e').collect(new Callable<ArrayList<Character>>() {
            @Override
            public ArrayList<Character> call() throws Exception {
                return new ArrayList<>();
            }
        }, new BiConsumer<ArrayList<Character>, Character>() {
            @Override
            public void accept(ArrayList<Character> mStrings, Character mCharacter) throws Exception {
                mStrings.add(mCharacter);
            }
        }).subscribe(new Consumer<ArrayList<Character>>() {
            @Override
            public void accept(ArrayList<Character> mCharacters) throws Exception {
                System.out.println(mCharacters);
            }
        });
    }

    private static void collectInfo() {
        Observable.just('c', 'a', 's', 's', 't', 'i','m','e').collectInto(new ArrayList<>(), new BiConsumer<ArrayList<Object>, Character>() {
            @Override
            public void accept(ArrayList<Object> mObjects, Character mCharacter) throws Exception {
                mObjects.add(mCharacter);
            }
        }).subscribe(new Consumer<ArrayList<Object>>() {
            @Override
            public void accept(ArrayList<Object> mObjects) throws Exception {
                System.out.println(mObjects);
            }
        });
    }



    private static void toList(){
        Observable.just('c', 'a', 's', 's', 't', 'i','m','e').toList().subscribe(new Consumer<List<Character>>() {
            @Override
            public void accept(List<Character> mCharacters) throws Exception {
                System.out.println(mCharacters);
            }
        });
    }


    private static void toSortedList(){
        Observable.just('c', 'a', 's', 's', 't', 'i','m','e').toSortedList(new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                return ((int)o1)-((int)o2);
            }
        }).subscribe(new Consumer<List<Character>>() {
            @Override
            public void accept(List<Character> mCharacters) throws Exception {
                System.out.println(mCharacters);
            }
        });
    }


    private static void toMap(){
        Observable.just('c', 'a', 's', 's', 't', 'i','m','e').map(new Function<Character, Student>() {
            @Override
            public Student apply(Character mCharacter) throws Exception {
                return new Student(mCharacter.toString(), 18);
            }
        }).toMap(new Function<Student, String>() {
            @Override
            public String apply(Student mStudent) throws Exception {
                return mStudent.name;
            }
        }).subscribe(new Consumer<Map<String, Student>>() {
            @Override
            public void accept(Map<String, Student> mStringStudentMap) throws Exception {
                System.out.println(mStringStudentMap);
            }
        });
    }

    private static void toMultimap(){
        Observable.just('c', 'a', 's', 's', 't', 'i','m','e').map(new Function<Character, Student>() {
            @Override
            public Student apply(Character mCharacter) throws Exception {
                return new Student(mCharacter.toString(), 18);
            }
        }).toMultimap(new Function<Student, String>() {
            @Override
            public String apply(Student mStudent) throws Exception {
                return mStudent.name;
            }
        }).subscribe(new Consumer<Map<String, Collection<Student>>>() {
            @Override
            public void accept(Map<String, Collection<Student>> mStringCollectionMap) throws Exception {
                System.out.println(mStringCollectionMap);
            }
        });
    }



}
