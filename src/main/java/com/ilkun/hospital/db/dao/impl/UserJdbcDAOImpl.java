package com.ilkun.hospital.db.dao.impl;

import com.ilkun.hospital.db.dao.UserDAO;
import com.ilkun.hospital.db.entity.Role;
import com.ilkun.hospital.db.entity.User;
import com.ilkun.hospital.exception.UserException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import org.apache.commons.dbutils.DbUtils;

/**
 * This class represents <tt>UserDAO</tt> implementation.
 * Uses JDBC interface for database connection.
 *
 * @author alexander-ilkun
 */
public class UserJdbcDAOImpl extends JdbcDAOSupportImpl implements UserDAO {

    private String hasLogin;
    private Integer hasActivated;
    private Role hasRole;

    /**
     * Saves entity to the database.
     * 
     * @param entity - entity to save in database
     * @return new id of entity
     * @throws UserException if database problem occurs.
     */
    @Override
    public int save(User entity) throws UserException {
        String query = queries.getProperty("HOSPITAL_USER_SAVE");
        Connection conToUse = null;
        CallableStatement cs = null;
        int status = 0;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setString(1, entity.getLogin());
            cs.setString(2, entity.getPassword());
            cs.setString(3, entity.getName());
            cs.setInt(4, entity.getActivated() ? 1 : 0);
            cs.registerOutParameter(5, OracleTypes.NUMBER);
            cs.executeUpdate();
            status = cs.getInt(5);
        } catch (SQLException e) {
            throw new UserException(
                    "%%% Exception occured in UserDAO save() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
        return status;
    }

    /**
     * Updates entity in the database.
     * 
     * @param entity - entity to update in database
     * @throws UserException if database problem occurs.
     */
    @Override
    public void update(User entity) throws UserException {
        String query = queries.getProperty("HOSPITAL_USER_UPDATE");
        Connection conToUse = null;
        CallableStatement cs = null;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setString(1, entity.getLogin());
            cs.setString(2, entity.getPassword());
            cs.setString(3, entity.getName());
            cs.setInt(4, entity.getActivated() ? 1 : 0);
            cs.setInt(5, entity.getId());
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new UserException(
                    "%%% Exception occured in UserDAO update() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
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
        String query = queries.getProperty("HOSPITAL_USER_DELETE");
        Connection conToUse = null;
        CallableStatement cs = null;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setInt(1, entity.getId());
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new UserException(
                    "%%% Exception occured in UserDAO delete() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
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
        String query = queries.getProperty("HOSPITAL_USER_GET_BY_ID");
        Connection conToUse = null;
        CallableStatement cs = null;
        User user = null;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setInt(2, id);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.executeQuery();
            ResultSet rs = (ResultSet) cs.getObject(1);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setName(rs.getString(4));
                user.setActivated(rs.getInt(5) == 1);
            }
        } catch (SQLException e) {
            throw new UserException(
                    "%%% Exception occured in UserDAO getById() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
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
        User user = getById(id);
        if (user != null) {
            initialize(user);
        }
        return user;
    }

    /**
     * Gets many entities from the database stored in the list.
     * Uses restrictions provided by hasXXXX() methods.
     * 
     * @param firstResult - index of first row starting from 1
     * @param maxResults - number of rows to be fetched
     * @return list of entities with specified restrictions
     * @throws UserException if database problem occurs.
     */
    @Override
    public List<User> getMany(int firstResult, int maxResults) throws UserException {
        String query = queries.getProperty("HOSPITAL_USER_GET_MANY");
        Connection conToUse = null;
        CallableStatement cs = null;
        List<User> users = new ArrayList<>();
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            if (hasLogin != null) {
                cs.setString(2, hasLogin);
            } else {
                cs.setNull(2, OracleTypes.VARCHAR);
            }
            if (hasActivated != null) {
                cs.setInt(3, hasActivated);
            } else {
                cs.setNull(3, OracleTypes.NUMBER);
            }
            if (hasRole != null) {
                cs.setInt(4, hasRole.getId());
            } else {
                cs.setNull(4, OracleTypes.NUMBER);
            }
            cs.setInt(5, firstResult + maxResults);
            cs.setInt(6, firstResult);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.executeQuery();
            ResultSet rs = (ResultSet) cs.getObject(1);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setName(rs.getString(4));
                user.setActivated(rs.getInt(5) == 1);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new UserException(
                    "%%% Exception occured in UserDAO getMany() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
        return users;
    }

    /**
     * Gets many initialized entities from the database stored in the list.
     * Uses restrictions provided by hasXXXX() methods.
     * 
     * @param firstResult - index of first row starting from 1
     * @param maxResults - number of rows to be fetched
     * @return list of entities with specified restrictions
     * @throws UserException if database problem occurs.
     */
    @Override
    public List<User> getInitializedMany(int firstResult, int maxResults) throws UserException {
        List<User> users = getMany(firstResult, maxResults);
        for (User user : users) {
            initialize(user);
        }
        return users;
    }

    /**
     * Adds restriction for getMany() and getInitializedMany() methods.
     * 
     * @param login - restriction object
     * @return dao object with restriction
     */
    @Override
    public UserDAO hasLogin(String login) {
        this.hasLogin = login;
        return this;
    }

    /**
     * Adds restriction for getMany() and getInitializedMany() methods.
     * 
     * @param activated - restriction object
     * @return dao object with restriction
     */
    @Override
    public UserDAO hasActivated(boolean activated) {
        this.hasActivated = activated ? 1 : null;
        return this;
    }

    /**
     * Adds restriction for getMany() and getInitializedMany() methods.
     * 
     * @param role - restriction object
     * @return dao object with restriction
     */
    @Override
    public UserDAO hasRole(Role role) {
        this.hasRole = role;
        return this;
    }

    /**
     * Initializes specified entity.
     * 
     * @param entity - entity to initialize from database
     * @return initialized entity
     * @throws UserPrescriptionException if database problem occurs.
     */
    private User initialize(User user) throws UserException {
        String query = queries.getProperty("HOSPITAL_USER_INITIALIZE");
        Connection conToUse = null;
        CallableStatement cs = null;
        List<Role> roles = new ArrayList<>();
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setInt(2, user.getId());
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.executeQuery();
            ResultSet rs = (ResultSet) cs.getObject(1);
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt(1));
                role.setName(rs.getString(2));
                roles.add(role);
            }
        } catch (SQLException e) {
            throw new UserException(
                    "%%% Exception occured in UserDAO initialize() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
        user.setRoles(roles);
        return user;
    }

}
