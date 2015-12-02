package com.ilkun.hospital.service;

import com.ilkun.hospital.db.entity.Role;
import com.ilkun.hospital.db.entity.User;
import com.ilkun.hospital.exception.RoleException;

/**
 * Customizes <tt>GenericService</tt> by entity, exception classes and 
 * custom query interface.
 * 
 * @author alexander-ilkun
 */
public interface RoleService extends GenericService<Role, RoleException> {

    /**
     * Returns query object for the service.
     * 
     * @return query object for the service
     */
    @Override
    RoleQuery query();
    
    /**
    * Customizes <tt>GenericQuery</tt> by entity, exception classes and 
    * custom restrictions on query.
    * 
    * @author alexander-ilkun
    */
    public interface RoleQuery extends GenericQuery<Role, RoleException> {

        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param name - restriction object
        * @return query object with restriction
        */
        RoleQuery hasName(String name);

        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param user - restriction object
        * @return query object with restriction
        */
        RoleQuery hasUser(User user);
    
    }

}
