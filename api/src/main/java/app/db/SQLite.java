package app.db;

import java.sql.*;

/**
 * Created by Bublik on 09-Nov-17.
 */
public class SQLite {

    private Connection connection = null;
    private Statement statement;

    public SQLite(String name) throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + name);
        statement = connection.createStatement();
        statement.setQueryTimeout(30);
    }

    public void executeUpdate(String s) throws SQLException {
        statement.executeUpdate(s);
    }

    public ResultSet select(String s) throws SQLException{
        return statement.executeQuery(s);
    }

    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
