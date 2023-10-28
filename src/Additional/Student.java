package Additional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Student implements Comparable<Student>{

    String name;
    int age;
    double gpa;

    public Student(String name,int age, double gpa) {
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public int compareTo(Student o) {

        return -this.name.compareTo(o.name);
    }
}

class studentNameComparator implements Comparator<Student> {


    @Override
    public int compare(Student o1, Student o2) {
        return o1.getName().compareTo(o2.getName());
    }
}

class Main {



    public static void main(String[] args) {


        ArrayList<Student> arr = new ArrayList<>();

        arr.add(new Student("Dauren",1,1) );
        arr.add(new Student("Arystan",1,1));
        arr.add(new Student("Anuar",1,1));
        arr.add(new Student("Beka",1,1));


        Collections.sort(arr);

        for (int i = 0; i <arr.size() ; i++) {
            System.out.println(arr.get(i).name);
        }



    }

}