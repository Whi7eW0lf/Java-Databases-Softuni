package orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {

    private static Connection connection;

    public static void createConnection(String username,
                                        String password,
                                        String driver,
                                        String connectionLink,
                                        String port,
                                        String databaseName) {

        Properties props = new Properties();
        props.setProperty("user",username);
        props.setProperty("password",password);

        String connectionString = "jdbc:"+driver+"://"+connectionLink+":"+port+"/"+databaseName;

        try {
            connection = DriverManager.getConnection(connectionString,props);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection(){
        return connection;
    }

}
