package ua.nure.kn.logvinova.usermanagement.db;

import static org.junit.Assert.*;

import junit.framework.TestCase;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.hsqldb.Database;
import org.junit.Before;
import org.junit.Test;
import ua.nure.kn.logvinova.usermanagement.User;

import java.util.Collection;
import java.util.Date;

public class HsqldbUserDAOTest extends DatabaseTestCase  {

    UserDAO dao;
    User user;
    ConnectionFactory connectionFactory;
    private Long id = new Long(1000);

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:db/usermanagement", "sa", "");
        return new DatabaseConnection(connectionFactory.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet = new XmlDataSet(getClass().getClassLoader()
                .getResourceAsStream("usersDataSet.xml"));
        return dataSet;
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        dao = DaoFactory.getInstance().getUserDAO();
        //dao = new HsqldbUserDAO(connectionFactory);
        user = new User("Kateryna","Logvinova",new Date());
    }

    @Test
    public void testCreate()  {
        try {
            assertNull(user.getId());
            user = dao.create(user);
            assertNotNull(user);
            assertNotNull(user.getId());
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }

    }
    @Test
    public void testFindAll() throws DatabaseException {
        Collection collection = dao.findAll();
        assertNotNull(collection);
        assertEquals(2, collection.size());
    }

    @Test
    public void testFind(){
        try{
            User user = dao.find(id);
            assertNotNull(user);
        } catch (DatabaseException e){
            e.printStackTrace();
            fail(e.toString());
        }
    }
    @Test
    public void testUpdate() throws Exception {
        try {
            User user = dao.find(id);
            dao.update(user);
            User newUser = dao.find(id);
            assertNotEquals(user, newUser);
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
    @Test
    public void testDelete() throws DatabaseException {
        dao.delete(id);
        assertEquals(1, dao.findAll().size());
    }

}