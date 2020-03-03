import entites.Customer;
import entites.Product;
import entites.Sale;
import entites.StoreLocation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exercise");
        EntityManager manager = factory.createEntityManager();

        Customer customer = new Customer("Test","asd@asd.com","1234-5678-90123-45678");

        manager.getTransaction().begin();
        manager.persist(customer);
        manager.getTransaction().commit();

        Product product = new Product("Nekuv Tam",5.0, BigDecimal.valueOf(69.69));

        manager.getTransaction().begin();
        manager.persist(product);
        manager.getTransaction().commit();

        StoreLocation storeLocation = new StoreLocation("Lunata");

        manager.getTransaction().begin();
        manager.persist(storeLocation);
        manager.getTransaction().commit();

        Sale sale = new Sale(product,customer,storeLocation,LocalDateTime.now());

        manager.getTransaction().begin();
        manager.persist(sale);
        manager.getTransaction().commit();

    }
}
