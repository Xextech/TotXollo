package cat.xtec.ioc.repository.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    public DBConnection(String connectionFile) throws ClassNotFoundException, IOException {
        Properties properties = new Properties();
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(connectionFile);

        if (input == null) {
            throw new IllegalArgumentException("No s'ha trobat el fitxer de connexió: " + connectionFile);
        }

        try {
            properties.load(input);
        } finally {
            input.close();
        }

        String driverClass = properties.getProperty("DB_DRIVER_CLASS");
        this.dbUrl = properties.getProperty("DB_URL");
        this.dbUsername = properties.getProperty("DB_USERNAME");
        this.dbPassword = properties.getProperty("DB_PASSWORD");

        if (driverClass == null || dbUrl == null || dbUsername == null || dbPassword == null) {
            throw new IllegalArgumentException("Falten propietats de connexió a: " + connectionFile);
        }

        Class.forName(driverClass);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }
}