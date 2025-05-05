package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private final Properties properties;
    private Connection connection = null;

    public JdbcUtils(Properties properties) {
        this.properties = properties;
    }

    public Connection getConnection() {
        try{
            if(connection == null || connection.isClosed()) {
                connection = getNewConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    private Connection getNewConnection() {
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        Connection newConnection = null;

        try {
            if(user != null && password != null) {
                newConnection = DriverManager.getConnection(url, user, password);
            } else {
                newConnection = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
            System.err.println(e.getErrorCode());
            System.err.println(e.getMessage());
        }

        return newConnection;
    }
}
