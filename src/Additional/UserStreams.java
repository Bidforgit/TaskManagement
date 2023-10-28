package Additional;

import chatGPTProject.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserStreams {

    private long id;
    private String firstName;

    @Override
    public String toString() {
        return "UserStreams{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", nationality='" + nationality + '\'' +
                '}';
    }

    private String lastName;
    private int age;
    private String nationality;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public UserStreams(long id, String firstName, String lastName, int age, String nationality) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.nationality = nationality;
    }


    public static void main(String[] args) {

        List<UserStreams> persons = getUsers();


        //filter, collect to list<
        List<UserStreams> collectoring = persons.stream()
                .filter(person -> person.getId() == 4)
                .collect(Collectors.toList());


        // sorted
        List<UserStreams> collect = persons.stream()
                .sorted(Comparator.comparing(UserStreams::getId))

                .collect(Collectors.toList());


        System.out.println(collect);
        System.out.println(collectoring);


    }

    private static List<UserStreams> getUsers(){

        return List.of(new UserStreams(45,"verv","we",14,"vr"),
                new UserStreams(4,"verv","we",14,"vr"),
                new UserStreams(1,"verv","we",14,"vr"),
                new UserStreams(2,"verv","we",14,"vr")
        );
    }

}


