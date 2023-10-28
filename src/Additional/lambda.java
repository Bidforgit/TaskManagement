package Additional;

import java.util.ArrayList;
import java.util.function.Predicate;

public class lambda {

    public static void main(String[] args) {


        ArrayList<String> arr = new ArrayList<>();

        arr.add("asc");
        arr.add("asc");
        arr.add("cbc");
        arr.add("wsc");
        arr.add("bscr");
        arr.add("ascraf");
        arr.add("asasdac");


        arr.removeIf(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.startsWith("a");
            }
        });



        // Используем лямбда-выражение для того же условия фильтрации
        arr.removeIf(s -> s.startsWith("A"));

        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
    }
}

