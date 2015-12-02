package com.ilkun.hospital.service.impl;

import com.ilkun.hospital.db.dao.RoleDAO;
import com.ilkun.hospital.db.dao.UserDAO;
import com.ilkun.hospital.db.dao.UserRoleDAO;
import com.ilkun.hospital.db.entity.Role;
import com.ilkun.hospital.db.entity.User;
import com.ilkun.hospital.db.entity.UserRole;
import com.ilkun.hospital.db.util.ConnectionPoolUtil;
import com.ilkun.hospital.exception.GenericException;
import com.ilkun.hospital.exception.ResourceHelperException;
import com.ilkun.hospital.exception.RoleException;
import com.ilkun.hospital.exception.UserException;
import com.ilkun.hospital.exception.UserRoleException;
import com.ilkun.hospital.factory.DAOFactory;
import com.ilkun.hospital.service.UserService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 * This class represents <tt>UserService</tt> implementation.
 * Uses dao layer for database operations.
 * 
 * @author alexander-ilkun
 */
public class UserServiceImpl implements UserService {

    /**
     * Saves entity to the database.
     * 
     * @param entity - entity to save in database
     * @throws UserException if database problem occurs.
     */
    @Override
    public void save(User entity) throws UserException {
        if (getById(entity.getId()) == null) {
            Connection conToUse = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                UserDAO userDAO = DAOFactory.getUserDAO();
                RoleDAO roleDAO = DAOFactory.getRoleDAO();
                UserRoleDAO userRoleDAO = DAOFactory.getUserRoleDAO();
                userDAO.setConnection(conToUse);
                roleDAO.setConnection(conToUse);
                userRoleDAO.setConnection(conToUse);
                UserRole userRole = new UserRole();

                // attaching the object
                int newId = userDAO.save(entity);
                entity.setId(newId);
                userRole.setUserId(entity.getId());
                for (Role role : entity.getRoles()) {
                    if (roleDAO.getById(role.getId()) == null) {
                        entity.setId(0);
                        throw new UserException("Role object doesn't attached");
                    }
                    userRole.setRoleId(role.getId());
                    userRoleDAO.save(userRole);
                }

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | RoleException | UserRoleException | UserException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new UserException(e.getMessage());
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
     * @throws UserException if database problem occurs.
     */
    @Override
    public void update(User entity) throws UserException {
        if (getById(entity.getId()) != null) {
            Connection conToUse = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                UserDAO userDAO = DAOFactory.getUserDAO();
                RoleDAO roleDAO = DAOFactory.getRoleDAO();
                UserRoleDAO userRoleDAO = DAOFactory.getUserRoleDAO();
                userDAO.setConnection(conToUse);
                roleDAO.setConnection(conToUse);
                userRoleDAO.setConnection(conToUse);
                UserRole userRole = new UserRole();
                userRole.setUserId(entity.getId());

                userRoleDAO.delete(userRole);
                userDAO.update(entity);
                for (Role role : entity.getRoles()) {
                    if (roleDAO.getById(role.getId()) == null) {
                        throw new UserException("Role object doesn't attached");
                    }
                    userRole.setRoleId(role.getId());
                    userRoleDAO.update(userRole);
                }

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new UserException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
        } else {
            throw new UserException("User object doesn't attached. Use save().");
        }
    }

    /**
     * Deletes entity from the database.
     * 
     * @param entity - entity to delete from database
     * @throws UserException if database problem occurs.
     */
    @Override
    public void delete(User entity) throws UserException {
        if (getById(entity.getId()) != null) {
            Connection conToUse = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                UserDAO userDAO = DAOFactory.getUserDAO();
                UserRoleDAO userRoleDAO = DAOFactory.getUserRoleDAO();
                userDAO.setConnection(conToUse);
                userRoleDAO.setConnection(conToUse);
                UserRole userRole = new UserRole();
                userRole.setUserId(entity.getId());

                userRoleDAO.delete(userRole);
                userDAO.delete(entity);

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new UserException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
        } else {
            throw new UserException("User object doesn't attached.");
        }
    }

    /**
     * Gets entity from the database.
     * 
     * @param id - id of entity in database
     * @return entity with specified id
     * @throws UserException if database problem occurs.
     */
    @Override
    public User getById(int id) throws UserException {
        Connection conToUse = null;
        User user = null;
        try {
            conToUse = ConnectionPoolUtil.getConnection();
            conToUse.setAutoCommit(false);
            UserDAO userDAO = DAOFactory.getUserDAO();
            userDAO.setConnection(conToUse);
            
            user = userDAO.getById(id);
            
            conToUse.commit();
        } catch (ResourceHelperException | SQLException | GenericException e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
            throw new UserException(e.getMessage());
        } finally {
            ConnectionPoolUtil.closeConnection(conToUse);
        }
        return user;
    }

    /**
     * Gets initialized entity from the database.
     * 
     * @param id - id of entity in database
     * @return entity with specified id
     * @throws UserException if database problem occurs.
     */
    @Override
    public User getInitializedById(int id) throws UserException {
        Connection conToUse = null;
        User user = null;
        try {
            conToUse = ConnectionPoolUtil.getConnection();
            conToUse.setAutoCommit(false);
            UserDAO userDAO = DAOFactory.getUserDAO();
            userDAO.setConnection(conToUse);
            
            user = userDAO.getInitializedById(id);

            conToUse.commit();
        } catch (ResourceHelperException | SQLException | GenericException e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
            throw new UserException(e.getMessage());
        } finally {
            ConnectionPoolUtil.closeConnection(conToUse);
        }
        return user;
    }

    /**
     * Returns query object for the service.
     * 
     * @return query object for the service
     */
    @Override
    public UserQueryImpl query() {
        return new UserQueryImpl();
    }

    /**
    * This class represents <tt>GenericQuery</tt> implementation.
    * Uses dao layer for database operations.
    * 
    * @author alexander-ilkun
    */
    public class UserQueryImpl implements UserQuery {

        private final UserDAO userDAO = DAOFactory.getUserDAO();

        private UserQueryImpl() {
        }

        /**
        * Gets many entities stored in the list.
        * Uses restrictions provided by hasXXXX() methods.
        * 
        * @param firstResult - index of first row starting from 1
        * @param maxResults - number of rows to be fetched
        * @return list of entities with specified restrictions
        * @throws UserException if application problem occurs.
        */
        @Override
        public List<User> find(int firstResult, int maxResults) throws UserException {
            Connection conToUse = null;
            List<User> users = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                userDAO.setConnection(conToUse);

                users = userDAO.getMany(firstResult, maxResults);

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new UserException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
            return users;
        }

        /**
        * Gets many initialized entities stored in the list.
        * Uses restrictions provided by hasXXXX() methods.
        * 
        * @param firstResult - index of first row starting from 1
        * @param maxResults - number of rows to be fetched
        * @return list of entities with specified restrictions
        * @throws UserException if application problem occurs.
        */
        @Override
        public List<User> findInitialized(int firstResult, int maxResults) throws UserException {
            Connection conToUse = null;
            List<User> users = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                userDAO.setConnection(conToUse);

                users = userDAO.getInitializedMany(firstResult, maxResults);

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new UserException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
            return users;
        }

        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param login - restriction object
        * @return query object with restriction
        */
        @Override
        public UserQuery hasLogin(String login) {
            userDAO.hasLogin(login);
            return this;
        }

        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param activated - restriction object
        * @return query object with restriction
        */
        @Override
        public UserQuery hasActivated(boolean activated) {
            userDAO.hasActivated(activated);
            return this;
        }

        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param role - restriction object
        * @return query object with restriction
        */
        @Override
        public UserQuery hasRole(Role role) {
            userDAO.hasRole(role);
            return this;
        }

    }

}
