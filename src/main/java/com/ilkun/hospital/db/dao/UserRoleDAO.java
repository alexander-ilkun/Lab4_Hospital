package com.ilkun.hospital.db.dao;

import com.ilkun.hospital.db.entity.UserRole;
import com.ilkun.hospital.exception.UserRoleException;

/**
 * Customizes <tt>GenericDAO</tt> by entity, exception classes and restrictions for it.
 * 
 * @author alexander-ilkun
 */
public interface UserRoleDAO extends GenericDAO<UserRole, UserRoleException>, JdbcDAOSupport {
    
}