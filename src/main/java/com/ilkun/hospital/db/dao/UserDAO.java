package com.ilkun.hospital.db.dao;

import com.ilkun.hospital.db.entity.Role;
import com.ilkun.hospital.db.entity.User;
import com.ilkun.hospital.exception.UserException;

/**
 * Customizes <tt>GenericDAO</tt> by entity, exception classes and restrictions for it.
 * 
 * @author alexander-ilkun
 */
public interface UserDAO extends GenericDAO<User, UserException>, JdbcDAOSupport {
    
    /**
     * Adds restriction for getMany() and getInitializedMany() methods.
     * 
     * @param login - restriction object
     * @return dao object with restriction
     */
    UserDAO hasLogin(String login);

    /**
     * Adds restriction for getMany() and getInitializedMany() methods.
     * 
     * @param activated - restriction object
     * @return dao object with restriction
     */
    UserDAO hasActivated(boolean activated);

    /**
     * Adds restriction for getMany() and getInitializedMany() methods.
     * 
     * @param role - restriction object
     * @return dao object with restriction
     */
    UserDAO hasRole(Role role);

}
