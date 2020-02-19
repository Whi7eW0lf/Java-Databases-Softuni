import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.sql.Connection;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Connector.createConnection(
                "root",
                "root",
                "mysql",
                "localhost",
                "3306",
                "test");

        Connection connection = Connector.getConnection();

        EntityManager<User> entityManager = new EntityManager<>(connection);

        User user = new User("Ivan", "sexbog34", 69, new Date());

        entityManager.persist(user);
    }
}
