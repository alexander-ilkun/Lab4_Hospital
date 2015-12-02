package com.ilkun.hospital.service;

import com.ilkun.hospital.db.entity.Role;
import com.ilkun.hospital.db.entity.User;
import com.ilkun.hospital.exception.UserException;

/**
 * Customizes <tt>GenericService</tt> by entity, exception classes and 
 * custom query interface.
 * 
 * @author alexander-ilkun
 */
public interface UserService extends GenericService<User, UserException> {

    /**
     * Returns query object for the service.
     * 
     * @return query object for the service
     */
    @Override
    UserQuery query();
    
    /**
    * Customizes <tt>GenericQuery</tt> by entity, exception classes and 
    * custom restrictions on query.
    * 
    * @author alexander-ilkun
    */
    public interface UserQuery extends GenericQuery<User, UserException> {

        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param login - restriction object
        * @return query object with restriction
        */
        UserQuery hasLogin(String login);
    
        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param activated - restriction object
        * @return query object with restriction
        */
        UserQuery hasActivated(boolean activated);
    
        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param role - restriction object
        * @return query object with restriction
        */
        UserQuery hasRole(Role role);
    
    }

}
