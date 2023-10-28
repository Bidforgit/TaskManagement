package DataStructures;


import java.util.ArrayList;
import java.util.LinkedList;

public class linklis {

    public static void main(String[] args) {

            checArrl();

            checlinked();

    }

    static void checlinked() {

        LinkedList<String> arr = new LinkedList<>();
        long start = System.currentTimeMillis();
        arr.add("cwecwecw");
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    static void checArrl() {

        ArrayList<String> arr = new ArrayList<>(10000);
        long start = System.currentTimeMillis();
        arr.add("csdcsdc");



        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }
}
