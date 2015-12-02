package com.ilkun.hospital.service.impl;

import com.ilkun.hospital.db.dao.RoleDAO;
import com.ilkun.hospital.db.dao.UserRoleDAO;
import com.ilkun.hospital.db.entity.Role;
import com.ilkun.hospital.db.entity.User;
import com.ilkun.hospital.db.entity.UserRole;
import com.ilkun.hospital.db.util.ConnectionPoolUtil;
import com.ilkun.hospital.exception.GenericException;
import com.ilkun.hospital.exception.ResourceHelperException;
import com.ilkun.hospital.exception.RoleException;
import com.ilkun.hospital.factory.DAOFactory;
import com.ilkun.hospital.service.RoleService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 * This class represents <tt>RoleService</tt> implementation.
 * Uses dao layer for database operations.
 * 
 * @author alexander-ilkun
 */
public class RoleServiceImpl implements RoleService {

    /**
     * Saves entity to the database.
     * 
     * @param entity - entity to save in database
     * @throws RoleException if database problem occurs.
     */
    @Override
    public void save(Role entity) throws RoleException {
        if (getById(entity.getId()) == null) {
            Connection conToUse = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                RoleDAO roleDAO = DAOFactory.getRoleDAO();
                roleDAO.setConnection(conToUse);

                // attaching the object
                int newId = roleDAO.save(entity);
                entity.setId(newId);

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | RoleException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new RoleException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
        } else {
            update(entity);
        }
    }

    /**
     * Updates entity in the database.
     * 
     * @param entity - entity to update in database
     * @throws RoleException if database problem occurs.
     */
    @Override
    public void update(Role entity) throws RoleException {
        if (getById(entity.getId()) != null) {
            Connection conToUse = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                RoleDAO roleDAO = DAOFactory.getRoleDAO();
                roleDAO.setConnection(conToUse);

                roleDAO.update(entity);

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new RoleException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
        } else {
            throw new RoleException("Role object doesn't attached. Use save().");
        }
    }

    /**
     * Deletes entity from the database.
     * 
     * @param entity - entity to delete from database
     * @throws RoleException if database problem occurs.
     */
    @Override
    public void delete(Role entity) throws RoleException {
        if (getById(entity.getId()) != null) {
            Connection conToUse = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                RoleDAO roleDAO = DAOFactory.getRoleDAO();
                UserRoleDAO userRoleDAO = DAOFactory.getUserRoleDAO();
                roleDAO.setConnection(conToUse);
                userRoleDAO.setConnection(conToUse);
                UserRole userRole = new UserRole();
                userRole.setRoleId(entity.getId());

                userRoleDAO.delete(userRole);
                roleDAO.delete(entity);

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new RoleException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
        } else {
            throw new RoleException("Role object doesn't attached.");
        }
    }

    /**
     * Gets entity from the database.
     * 
     * @param id - id of entity in database
     * @return entity with specified id
     * @throws RoleException if database problem occurs.
     */
    @Override
    public Role getById(int id) throws RoleException {
        Connection conToUse = null;
        Role role = null;
        try {
            conToUse = ConnectionPoolUtil.getConnection();
            conToUse.setAutoCommit(false);
            RoleDAO roleDAO = DAOFactory.getRoleDAO();
            roleDAO.setConnection(conToUse);
            
            role = roleDAO.getById(id);
            
            conToUse.commit();
        } catch (ResourceHelperException | SQLException | GenericException e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
            throw new RoleException(e.getMessage());
        } finally {
            ConnectionPoolUtil.closeConnection(conToUse);
        }
        return role;
    }

    /**
     * Gets initialized entity from the database.
     * 
     * @param id - id of entity in database
     * @return entity with specified id
     * @throws RoleException if database problem occurs.
     */
    @Override
    public Role getInitializedById(int id) throws RoleException {
        Connection conToUse = null;
        Role role = null;
        try {
            conToUse = ConnectionPoolUtil.getConnection();
            conToUse.setAutoCommit(false);
            RoleDAO roleDAO = DAOFactory.getRoleDAO();
            roleDAO.setConnection(conToUse);
            
            role = roleDAO.getInitializedById(id);
            
            conToUse.commit();
        } catch (ResourceHelperException | SQLException | GenericException e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
            throw new RoleException(e.getMessage());
        } finally {
            ConnectionPoolUtil.closeConnection(conToUse);
        }
        return role;
    }

    /**
     * Returns query object for the service.
     * 
     * @return query object for the service
     */
    @Override
    public RoleQueryImpl query() {
        return new RoleQueryImpl();
    }

    /**
    * This class represents <tt>GenericQuery</tt> implementation.
    * Uses dao layer for database operations.
    * 
    * @author alexander-ilkun
    */
    public class RoleQueryImpl implements RoleQuery {

        private final RoleDAO roleDAO = DAOFactory.getRoleDAO();

        private RoleQueryImpl() {
        }

        /**
        * Gets many entities stored in the list.
        * Uses restrictions provided by hasXXXX() methods.
        * 
        * @param firstResult - index of first row starting from 1
        * @param maxResults - number of rows to be fetched
        * @return list of entities with specified restrictions
        * @throws RoleException if application problem occurs.
        */
        @Override
        public List<Role> find(int firstResult, int maxResults) throws RoleException {
            Connection conToUse = null;
            List<Role> roles = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                roleDAO.setConnection(conToUse);
                roles = roleDAO.getMany(firstResult, maxResults);
                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new RoleException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
            return roles;
        }

        /**
        * Gets many initialized entities stored in the list.
        * Uses restrictions provided by hasXXXX() methods.
        * 
        * @param firstResult - index of first row starting from 1
        * @param maxResults - number of rows to be fetched
        * @return list of entities with specified restrictions
        * @throws RoleException if application problem occurs.
        */
        @Override
        public List<Role> findInitialized(int firstResult, int maxResults) throws RoleException {
            Connection conToUse = null;
            List<Role> roles = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                roleDAO.setConnection(conToUse);
                
                roles = roleDAO.getInitializedMany(firstResult, maxResults);
                
                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new RoleException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
            return roles;
        }

        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param name - restriction object
        * @return query object with restriction
        */
        @Override
        public RoleQuery hasName(String name) {
            roleDAO.hasName(name);
            return this;
        }

        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param user - restriction object
        * @return query object with restriction
        */
        @Override
        public RoleQuery hasUser(User user) {
            roleDAO.hasUser(user);
        return this;
        }
    
    }

}
