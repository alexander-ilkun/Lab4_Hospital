package com.ilkun.hospital.db.dao;

import com.ilkun.hospital.db.entity.Role;
import com.ilkun.hospital.db.entity.User;
import com.ilkun.hospital.exception.RoleException;

/**
 * Customizes <tt>GenericDAO</tt> by entity, exception classes and restrictions for it.
 * 
 * @author alexander-ilkun
 */
public interface RoleDAO extends GenericDAO<Role, RoleException>, JdbcDAOSupport {
    
    /**
     * Adds restriction for getMany() and getInitializedMany() methods.
     * 
     * @param name - restriction object
     * @return dao object with restriction
     */
    RoleDAO hasName(String name);

    /**
     * Adds restriction for getMany() and getInitializedMany() methods.
     * 
     * @param user - restriction object
     * @return dao object with restriction
     */
    RoleDAO hasUser(User user);

}
