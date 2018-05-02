package com.ersystem.util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private Properties prop = new Properties();
    private static ConnectionUtil connUtil = new ConnectionUtil();
    
    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private ConnectionUtil() {
        super();
        try {
        		InputStream dbProps = ConnectionUtil.class.getClassLoader().getResourceAsStream("database.properties");
            prop.load(dbProps);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"),
                prop.getProperty("password"));
    }
    public static ConnectionUtil getConnectionUtil() {
        return connUtil;
    }
}