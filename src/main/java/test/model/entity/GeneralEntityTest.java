package test.model.entity;

import org.hibernate.Session;
import org.junit.Test;
import ru.unc6.promeets.model.entity.File;
import ru.unc6.promeets.model.entity.User;
import test.model.BaseDatabaseTest;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Vladimir on 31.01.2016.
 */
public class GeneralEntityTest extends BaseDatabaseTest {

    @Test
    public void testUser() {
        User user = new User();
        Session session = sessionFactory.openSession();
        user.setUserId(123);
        user.setFirstName("test user 1");
        user.setLastName("zbs");
        user.setPassword("asdsad");
        user.setEmail("zbcemail@mail.com");
        user.setImage(session.load(File.class,(long)1));
        session.beginTransaction();
        long id = (long) session.save(user);
        /*user = session.load(User.class, id);
        session.delete(user);*/
        session.getTransaction().commit();
        session.close();
    }


}
