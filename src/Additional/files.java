package Additional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class files {

    static List<User> users = new ArrayList<>();
    public static List<User> getUsersList() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("memory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String login = parts[1];
                    String password = parts[2];
                    User user = new User(id, login, password);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void saveUsersList(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("memory.txt"))) {
            for (User user : users) {
                writer.write(user.getId() + "," + user.getLogin() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        List<User> users = new ArrayList<>(3);

        users.add(new User(4,"gerg","ver"));


        users.add(new User(5,"gerg","ver"));

        users.add(new User(6,"gerg","ver"));


        saveUsersList(users);
        getUsersList();

    }
}

class User{
    int id;
    String login;
    String password;

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}