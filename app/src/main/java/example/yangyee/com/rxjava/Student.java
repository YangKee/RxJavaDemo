package example.yangyee.com.rxjava;

public class Student  extends person {

    private int age;
    public Student(String mName, int mAge) {
       super(mName);
        age = mAge;
    }



    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}