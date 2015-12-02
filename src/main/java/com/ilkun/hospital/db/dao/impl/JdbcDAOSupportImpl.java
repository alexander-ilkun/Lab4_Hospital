package com.ilkun.hospital.db.dao.impl;

import com.ilkun.hospital.db.dao.JdbcDAOSupport;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents <tt>JdbcDAOSupprot</tt> implementation.
 * Also this class responsible for loading queries from the property file.
 *
 * @author alexander-ilkun
 */
public abstract class JdbcDAOSupportImpl implements JdbcDAOSupport {

    private Connection connection;
    protected static Properties queries = new Properties();
    
    static {
        try {
            queries.load(JdbcDAOSupportImpl.class.getClassLoader()
                    .getResourceAsStream("db-queries.properties"));
        } catch (IOException ex) {
            Logger.getLogger(JdbcDAOSupportImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
