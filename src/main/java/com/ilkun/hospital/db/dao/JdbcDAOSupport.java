package com.ilkun.hospital.db.dao;

import java.sql.Connection;

/**
 * Provides JDBC support by adding methods for work with connection.
 * 
 * @author alexander-ilkun
 */
public interface JdbcDAOSupport {
    
    Connection getConnection();

    void setConnection(Connection connection);

}
