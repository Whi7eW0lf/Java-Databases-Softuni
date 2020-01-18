import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class DiabloDatabaseStats {
    public static void main(String[] args) throws SQLException {

        Scanner read = new Scanner(System.in);

        Properties prop = new Properties();
        prop.setProperty("user", "root");
        prop.setProperty("password", "root");

        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/diablo", prop);

        PreparedStatement statement = connection.prepareStatement(
                "SELECT CONCAT(first_name, ' ', last_name) AS `full_name`, `user_id`\n" +
                        "FROM `users_games`,`users`" +
                        "WHERE user_id = (SELECT `id` FROM `users` WHERE `user_name` = ?)\n" +
                        "AND `user_name` = ?;");

        String username = read.nextLine();

        statement.setString(1, username);
        statement.setString(2, username);

        ResultSet set = statement.executeQuery();

        int count = 0;

        while (set.next()) {
            count++;
        }

        if (count > 0) {
            set.first();
            System.out.println(String.format("User: %s%n%s has played %d games", username, set.getString("full_name"), count));
        }else {
            System.out.println("No such user exists");
        }
    }
}