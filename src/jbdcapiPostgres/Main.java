package jbdcapiPostgres;

import java.sql.*;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

public class Main {

    private static Connection connection;
    private static final String user = "postgres";
    private static final String password = "123";

    public static void main(String[] args) throws SQLException, ParseException {
        Scanner sc= new Scanner(System.in);

    //  user:login    password:
        //1 loginim  parol
        //2 test1    passworr
        //3 user1    user1

        try{
            String url = "jdbc:postgresql://localhost:5432/northwind";
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
        String pass;
        switch (val) {
            case 1:
                System.out.print("Enter your username: ");
                login = sc.nextLine();
                System.out.print("Enter your password: ");
                pass = sc.nextLine();

                boolean authenticated = authenticateUser(login,pass);
                System.out.println(authenticated);

                while (!authenticated) {
                    System.out.println("Authentication failed. PLS try gain!");

                    System.out.print("Enter your username: ");
                    login = sc.nextLine();
                    System.out.print("Enter your password: ");
                    pass = sc.nextLine();
                    authenticated = authenticateUser(login, pass);
                    System.out.println(authenticated);
                }
                break;
            case 2:
                System.out.print("Enter your username: ");
                login = sc.nextLine();
                System.out.print("Enter your password: ");
                pass = sc.nextLine();
                System.out.println(registerUser(login,pass));
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
            switch (val) {
                case 1:
                    sc.nextLine();
                    title= sc.nextLine();
                    desc = sc.nextLine();
                    creation = sc.nextLine();
                    status = sc.nextLine();
                    addTask(title,desc,creation,status,login);
                    break;
                case 2:
                    int task_id = sc.nextInt();
                    sc.nextLine();
                    title= sc.nextLine();
                    desc = sc.nextLine();
                    creation = sc.nextLine();
                    status = sc.nextLine();
                    updateTask(task_id,title,desc,creation,status,login);
                    //2023-12-12 12:21:23.000000
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
                    sc.nextLine();
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

        String query = "INSERT INTO users (username, password_hash, salt) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, salt);
            return preparedStatement.executeUpdate() == 1;
        }

    }

    public static void addTask(String title, String description, String creation_date, String status, String username) throws SQLException {
        String query = "INSERT INTO tasks (title, description, creation_date,status,user_id) VALUES (?, ?, ?,?, " +
                "(SELECT user_id FROM users WHERE username = ?)) ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date parsedDate = dateFormat.parse(creation_date);
                Timestamp timestamp = new Timestamp(parsedDate.getTime());
                preparedStatement.setTimestamp(3, timestamp);
            } catch (java.text.ParseException e) {
                // Handle the case where the date is not in the correct format
                System.err.println("Invalid date format: " + creation_date);
                // You can choose to handle or log the error appropriately
            }
            preparedStatement.setString(4, status);
            preparedStatement.setString(5, username);
            preparedStatement.executeUpdate();
        }
    }

    public static boolean updateTask(int task_id, String title, String description, String creation_date, String status, String username) throws SQLException, ParseException {
        String query = "UPDATE tasks\n" +
                "SET title = ?, description = ?, creation_date = ?, status = ?\n" +
                "WHERE task_id = ? and user_id = (SELECT user_id FROM users WHERE username = ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date parsedDate = dateFormat.parse(creation_date);
                Timestamp timestamp = new Timestamp(parsedDate.getTime());
                preparedStatement.setTimestamp(3, timestamp);
            } catch (java.text.ParseException e) {
                // Handle the case where the date is not in the correct format
                System.err.println("Invalid date format: " + creation_date);
                // You can choose to handle or log the error appropriately
            }

            preparedStatement.setString(4, status);
            preparedStatement.setInt(5, task_id);
            preparedStatement.setString(6, username);

            return preparedStatement.executeUpdate() == 1;
        }
    }

    public static void dropTask(int task_id) {
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
    public static void getTasksFromUser(String username){
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
    public static void filterTasksByStatus(String username, String status) {
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

                return BCrypt.checkpw(password, hashedPassword);
            }
            return false;
        }
    }

}


