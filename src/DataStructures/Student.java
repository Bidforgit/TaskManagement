package DataStructures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Student {

String name;

    public Student(String name, int age, double gpa) {
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }

    int age;
double gpa;

    public static void main(String[] args) {
        List<Student> ar = new ArrayList<>();

        Student std = new Student("ap",12,4);
        Student std1 = new Student("ap8",12,3);
        Student std2 = new Student("ap5",12,5);
        Student std3 = new Student("ap5",12,4);
        Student std4 = new Student("ap6",12,9);
        ar.add(std);
        ar.add(std1);
        ar.add(std2);
        ar.add(std3);
        ar.add(std4);

        Iterator<Student> iterator = ar.iterator();
        double max = 0;
        double min = 99;

        double sum = 0;

        while(iterator.hasNext()){
            Student student = iterator.next();
            sum += student.gpa;
            System.out.println(student.gpa);
            if(max < student.gpa)
                max = student.gpa;
            if(min > student.gpa){
                min = student.gpa;
            }

        }

        System.out.println(min);
        System.out.println(max);
        System.out.println(sum / ar.size());



    }
}
