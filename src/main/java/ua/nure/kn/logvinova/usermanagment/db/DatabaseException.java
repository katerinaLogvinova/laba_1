package ua.nure.kn.logvinova.usermanagement.db;

import java.sql.SQLException;

public class DatabaseException extends Exception {

    public DatabaseException(SQLException e) {
        super(e);
    }

    public DatabaseException(String s) {
        super(s);
    }
}