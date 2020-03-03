import entites.GringottsDatabase.Wizard;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Date;

public class Engine implements Runnable{

    private EntityManager manager;

    public Engine(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {

        gringottsDatabase();

    }

    private void gringottsDatabase() {

        Wizard wizard = new Wizard("Test",
                "Test",
                null,
                50,
                "asd",
                50,
                "Asd",
                new Date(),
                BigDecimal.ONE,
                BigDecimal.TEN,
                BigDecimal.ONE,
                new Date(),
                false);


        this.manager.getTransaction().begin();
        this.manager.persist(wizard);
        this.manager.getTransaction().commit();
    }


}
