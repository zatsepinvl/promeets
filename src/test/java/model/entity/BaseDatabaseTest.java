package model.entity;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Vladimir on 31.01.2016.
 */
public class BaseDatabaseTest {
    protected SessionFactory sessionFactory;
    protected EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() {
        /*sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addPackage("ru.unc6.promeets.model.entity")
                .buildSessionFactory();*/
        entityManagerFactory = Persistence.createEntityManagerFactory("item-manager");
    }
}
