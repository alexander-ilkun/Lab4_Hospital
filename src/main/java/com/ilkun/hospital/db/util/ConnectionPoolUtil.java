package com.ilkun.hospital.db.util;

import com.ilkun.hospital.exception.ResourceHelperException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

/**
 * This class represents connection pool.
 * Uses settings from the properties file.
 *
 * @author alexander-ilkun
 */
public class ConnectionPoolUtil {
    
    private final static PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();
    private final static Properties properties = new Properties();
    
    static {
        try {
            properties.load(ConnectionPoolUtil.class.getClassLoader()
                    .getResourceAsStream("db-config.properties"));
            pds.setConnectionFactoryClassName(properties.getProperty("DATABASE_CONNECTION_FACTORY_CLASS_NAME"));
            pds.setURL(properties.getProperty("DATABASE_URL"));
            pds.setUser(properties.getProperty("DATABASE_USER"));
            pds.setPassword(properties.getProperty("DATABASE_PASSWORD"));
            pds.setMinPoolSize(Integer.parseInt(properties.getProperty("POOL_MIN_SIZE")));
            pds.setMaxPoolSize(Integer.parseInt(properties.getProperty("POOL_MAX_SIZE")));
            pds.setTimeoutCheckInterval(Integer.parseInt(properties.getProperty("POOL_TIMEOUT_CHECK_INTERVAL")));
            pds.setTimeToLiveConnectionTimeout(Integer.parseInt(properties.getProperty("POOL_TIME_TO_LIVE_CONNECTION_TIMEOUT")));
            pds.setAbandonedConnectionTimeout(Integer.parseInt(properties.getProperty("POOL_ABANDONED_CONNECTION_TIMEOUT")));
            pds.setInactiveConnectionTimeout(Integer.parseInt(properties.getProperty("POOL_INACTIVE_CONNECTION_TIMEOUT")));
            pds.setConnectionWaitTimeout(Integer.parseInt(properties.getProperty("POOL_CONNECTION_WAIT_TIMEOUT")));
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ConnectionPoolUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Connection getConnection() throws ResourceHelperException {
        try {
            return pds.getConnection();
        } catch (SQLException e) {
            System.err.println("%%% SQLException %%%" + e.getMessage());
            throw new ResourceHelperException(e.getMessage());
        }
    }
    
    public static void closeConnection(Connection connection) {
        DbUtils.closeQuietly(connection);
    }
    
    public static void main(String[] args) {
        for (int i = 0; i < 25; i++) {
            try {
                getConnection();
                System.out.println(i);
            } catch (ResourceHelperException ex) {
                Logger.getLogger(ConnectionPoolUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
