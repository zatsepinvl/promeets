package test.model;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;

import java.io.File;

/**
 * Created by Vladimir on 31.01.2016.
 */
public class BaseDatabaseTest {
    protected SessionFactory sessionFactory;

    @Before
    public void setUp() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addPackage("ru.unc6.promeets.model.entity")
                .buildSessionFactory();
    }
}
