package com.ilkun.hospital.db.util;

import com.ilkun.hospital.exception.ResourceHelperException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.dbutils.DbUtils;

/**
 * This class represents connection utility.
 * Uses settings from the properties file.
 *
 * @author alexander-ilkun
 */
public class ConnectionUtil {

    private final static Properties properties = new Properties();

    static {
        try {
            properties.load(ConnectionUtil.class.getClassLoader()
                    .getResourceAsStream("db-config.properties"));
        } catch (IOException e) {
            System.out.println("%%%% db-config.properties file load error%%%%%"
                    + e);
        }

    }

    public static Connection getConnection() throws ResourceHelperException {
        Connection connection = null;
        try {
            Class.forName(properties.getProperty("DATABASE_DRIVER_NAME"));
            try {
                connection = DriverManager.getConnection(properties
                        .getProperty("DATABASE_URL"),
                        properties.getProperty("DATABASE_USER"), properties
                        .getProperty("DATABASE_PASSWORD"));
            } catch (SQLException e) {
                System.err.println("%%% SQLException %%%" + e.getMessage());
                throw new ResourceHelperException(e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.err.println("%%% Driver Class Not found %%%" + e);
            throw new ResourceHelperException(e.getMessage());
        }
        return connection;

    }

    public static void closeConnection(Connection connection) {
        DbUtils.closeQuietly(connection);
    }

    public static void main(String[] args) throws Exception {
        Connection conToUse = getConnection();
        System.out.println(conToUse);
        closeConnection(conToUse);
    }

}
