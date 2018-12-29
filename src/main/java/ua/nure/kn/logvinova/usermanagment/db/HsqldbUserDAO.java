package ua.nure.kn.logvinova.usermanagement.db;

import ua.nure.kn.logvinova.usermanagement.User;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

class HsqldbUserDAO implements UserDAO {

    private static final String INSERT_QUERY = "INSERT INTO USERS (FIRSTNAME, LASTNAME, DATEOFBIRTH) VALUES(?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT* FROM users";
    private static final String UPDATE_QUERY = "UPDATE users Set firstname=?, lastname=?, dateofbirth=? WHERE id=?";
    private static final String SELECT_BY_ID = "SELECT id, firstname, lastname, dateofbirth FROM users WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM users Where id=?";

    ConnectionFactory connectionFactory;

    public HsqldbUserDAO() {}

    public  HsqldbUserDAO(ConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
    }
    @Override
    public User create(User user) throws DatabaseException {

        try{Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));

            int n = statement.executeUpdate();
            if (n != 1) throw new DatabaseException("Numbers of Inserted rows: " + n);
            CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
            ResultSet keys = callableStatement.executeQuery();

            if (keys.next()) {
                user.setId(keys.getLong(1));
            }
            keys.close();
            callableStatement.close();
            statement.close();
            connection.close();
            return user;
        }
        catch(DatabaseException e) {throw e;}
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void update(User user) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            statement.setLong(4, user.getId().longValue());
            if (statement.executeUpdate() != 1)
                throw new DatabaseException("Updated rows: " + statement.executeUpdate());
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

    }


    @Override
    public void delete(Long id) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setString(1, id.toString());

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted != 1) throw new DatabaseException("Deleted rows: " + rowsDeleted);

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public User find(Long id) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id.longValue());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                throw new DatabaseException("Couldn't find user id: " + id);
            User result = new User();
            result.setId(new Long(resultSet.getLong(1)));
            result.setFirstName(resultSet.getString(2));
            result.setLastName(resultSet.getString(3));
            result.setDateOfBirth(resultSet.getDate(4));
            return result;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        Collection<User> result = new LinkedList<User>();

        try {
            Connection connection = connectionFactory.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
            while(resultSet.next()) {
                User user = new User();
                user.setId(new Long(resultSet.getLong(1)));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setDateOfBirth(resultSet.getDate(4));
                result.add(user);
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        return result;

    }
    @Override
    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
}