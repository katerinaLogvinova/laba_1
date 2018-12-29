package ua.nure.kn.logvinova.usermanagement.db;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DaoFactoryTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetUserDAO() {
        try {
            DaoFactory daoFactory = DaoFactory.getInstance();
            assertNotNull(daoFactory);
            UserDAO userDAO = daoFactory.getUserDAO();
            assertNotNull(userDAO);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }


}