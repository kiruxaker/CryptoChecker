package net.cryptochecker.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class MyDataSource {

    private static final Logger LOGGER = Logger.getLogger(MyDataSource.class);

    @Value("${spring.datasource.password}")
    private String pass;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.url}")
    private String url;

    public Connection getConnection() throws SQLException{
        LOGGER.info("Get connection...");
        Connection connect = DriverManager.getConnection(url, user, pass);
        connect.createStatement().execute("SET time_zone='+00:00'");
        return connect;
    }

    public void closeConnection(Connection connection){
        if (connection == null) return;
        try {
            LOGGER.info("Close connection...");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
    }

}
