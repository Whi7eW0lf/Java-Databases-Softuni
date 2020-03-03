import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("exercise");
        EntityManager manager = managerFactory.createEntityManager();

        Engine engine = new Engine(manager);
        engine.run();

    }
}
