package jbdcapiPostgres;

import java.sql.*;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.SQLException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.*;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

class UserService {
    private DatabaseMetaData DatabaseConnection;


}


public class Main {

    private static Connection connection;
    private static String url = "jdbc:postgresql://localhost:5432/northwind";
    private static String user = "postgres";
    private static String password = "123";

    public static void main(String[] args) throws SQLException {
        Scanner sc= new Scanner(System.in);

        // login    password:
        //1 loginim  parol
        //2 test1    passworr

        try{
        connection = DriverManager.getConnection(url,user,password);
            System.out.println("Connected");
        }catch (Exception e){
            e.printStackTrace();
        }
        int val = 0;
        System.out.println("to auth -> 1:");
        System.out.println("to register -> 2:");
        val = sc.nextInt();
        sc.nextLine();

        String login = null;
        String pass = null;
        switch (val) {
            case 1:
                System.out.print("Enter your username: ");
                login = sc.nextLine();
                System.out.print("Enter your password: ");
                pass = sc.nextLine();

                boolean authenticated = authenticateUser(login,pass);
                System.out.println(authenticated);

                while (!authenticated) {
                    if (authenticated) {
                        System.out.println("You are logged in.");
                    } else {
                        System.out.println("Authentication failed. PLS try gain!");
                    }
                    System.out.print("Enter your username: ");
                    login = sc.nextLine();
                    System.out.print("Enter your password: ");
                    pass = sc.nextLine();
                    authenticated = authenticateUser(login, pass);
                    System.out.println(authenticated);
                }
                break;
            case 2:
                login = sc.nextLine();
                pass = sc.nextLine();
                registerUser(login,pass);
                System.out.println("You re registered");
                break;
        }
        while (true) {
            String title;
            String desc;
            String creation;
            String status;

            System.out.println("to create task -> 1:");
            System.out.println("to update task -> 2:");
            System.out.println("to delete task -> 3:");
            System.out.println("to view tasks -> 4:");
            System.out.println("to filter tasks by status : -> 5:");
            System.out.println("to EXIT -> 6:");

            val = sc.nextInt();
            sc.nextLine();
            switch (val) {
                case 1:
                   title= sc.nextLine();
                    desc = sc.nextLine();
                    creation = sc.nextLine();
                    status = sc.nextLine();
                    addTask(title,desc,creation,status,login);
                    break;
                case 2:

                    title= sc.nextLine();
                    desc = sc.nextLine();
                    creation = sc.nextLine();
                    status = sc.nextLine();
                    updateTask(1,title,desc,creation,status,login);

                    break;
                case 3:
                    getTasksFromUser(login);
                    System.out.println("What to drop here");
                    int tas = sc.nextInt();
                    dropTask(tas);
                    break;
                case 4:
                    getTasksFromUser(login);
                    break;
                case 5:
                    status = sc.nextLine();
                    filterTasksByStatus(login,status);
                    break;
                case 6:
                    System.exit(0);
            }

        }

    }

    public static boolean registerUser(String username, String password) throws SQLException {
        String salt = BCrypt.gensalt(); // Генерируем соль
        String hashedPassword = BCrypt.hashpw(password, salt); // Хэшируем пароль
        DatabaseMetaData DatabaseConnection;
        String query = "INSERT INTO users (username, password_hash, salt) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, salt);
            return preparedStatement.executeUpdate() == 1;
        }

    }

    public static boolean addTask(String title, String description,String creation_date,String status,String username) throws SQLException {
        String query = "INSERT INTO tasks (title, description, creation_date,status,user_id) VALUES (?, ?, ?,?, " +
                "(SELECT user_id FROM users WHERE username = ?)) ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(creation_date));
            preparedStatement.setString(4, status);
            preparedStatement.setString(5, username);
            return preparedStatement.executeUpdate() == 1;
        }
    }

    public static boolean updateTask(int task_id,String title, String description,String creation_date,String status,String username) throws SQLException {
        String query = "UPDATE tasks\n" +
                "SET title = ?, description = ?, creation_date = ?, status = ?\n" +
                "WHERE task_id = ? and user_id = (SELECT user_id FROM users WHERE username = ?);\n";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(creation_date));
            preparedStatement.setString(4, status);
            preparedStatement.setInt(5, task_id);  // Set task_id
            preparedStatement.setString(6, username);

            return preparedStatement.executeUpdate() == 1;
        }
    }
    public static void dropTask(int task_id) throws SQLException {
        try{
            Statement statement = connection.createStatement();
            String query = "DELETE FROM tasks \n" +
                    " WHERE task_id = " + task_id;
            statement.executeUpdate(query);
            statement.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getTasksFromUser(String username) throws SQLException {
        try {

            Statement statement = connection.createStatement();
            String query = "SELECT * FROM tasks WHERE user_id = (SELECT user_id FROM users WHERE username = ?) GROUP BY task_id";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int task_id = resultSet.getInt("task_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Timestamp creation_date = resultSet.getTimestamp("creation_date");
                String status = resultSet.getString("status");
                System.out.println("task_id:" + task_id + "; title:" + title + "; description:" + description +
                        "; creation:" + creation_date+ "; status:" + status);
            }
            statement.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void filterTasksByStatus(String username, String status) throws SQLException {
        try {
            String query = "SELECT * FROM tasks WHERE status = ? " +
                    "and user_id = (SELECT user_id FROM users WHERE username = ?) GROUP BY task_id";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, status);
            preparedStatement.setString(2, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int task_id = resultSet.getInt("task_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Timestamp creation_date = resultSet.getTimestamp("creation_date");
                int user_id = resultSet.getInt("user_id");
                System.out.println("task_id:" + task_id + "; title:" + title + "; description:" + description +
                        "; creation:" + creation_date+ "; status:" + status + "; user_id:" + user_id);
            }
          preparedStatement.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }




    public static boolean authenticateUser(String username, String password) throws SQLException {
        String query = "SELECT password_hash, salt FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password_hash");
                String salt = resultSet.getString("salt");


                // Use BCrypt to check if the provided password matches the stored hash and salt.
                String hashed = BCrypt.hashpw(password, BCrypt.gensalt(10));

                return BCrypt.checkpw(password, hashedPassword);
            }

            return false;
        }
    }



}


