package com.ilkun.hospital.db.dao.impl;

import com.ilkun.hospital.db.dao.RoleDAO;
import com.ilkun.hospital.db.entity.Role;
import com.ilkun.hospital.db.entity.User;
import com.ilkun.hospital.exception.RoleException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import org.apache.commons.dbutils.DbUtils;

/**
 * This class represents <tt>RoleDAO</tt> implementation.
 * Uses JDBC interface for database connection.
 *
 * @author alexander-ilkun
 */
public class RoleJdbcDAOImpl extends JdbcDAOSupportImpl implements RoleDAO {

    private String hasName;
    private User hasUser;

    /**
     * Saves entity to the database.
     * 
     * @param entity - entity to save in database
     * @return new id of entity
     * @throws RoleException if database problem occurs.
     */
    @Override
    public int save(Role entity) throws RoleException {
        String query = queries.getProperty("HOSPITAL_ROLE_SAVE");
        Connection conToUse = null;
        CallableStatement cs = null;
        int status = 0;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setString(1, entity.getName());
            cs.registerOutParameter(2, OracleTypes.NUMBER);
            cs.executeUpdate();
            status = cs.getInt(2);
        } catch (SQLException e) {
            throw new RoleException(
                    "%%% Exception occured in RoleDAO save() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
        return status;
    }

    /**
     * Updates entity in the database.
     * 
     * @param entity - entity to update in database
     * @throws RoleException if database problem occurs.
     */
    @Override
    public void update(Role entity) throws RoleException {
        String query = queries.getProperty("HOSPITAL_ROLE_UPDATE");
        Connection conToUse = null;
        CallableStatement cs = null;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setString(1, entity.getName());
            cs.setInt(2, entity.getId());
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new RoleException(
                    "%%% Exception occured in RoleDAO update() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
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
        String query = queries.getProperty("HOSPITAL_ROLE_DELETE");
        Connection conToUse = null;
        CallableStatement cs = null;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setInt(1, entity.getId());
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new RoleException(
                    "%%% Exception occured in RoleDAO delete() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
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
        String query = queries.getProperty("HOSPITAL_ROLE_GET_BY_ID");
        Connection conToUse = null;
        CallableStatement cs = null;
        Role role = null;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setInt(2, id);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.executeQuery();
            ResultSet rs = (ResultSet) cs.getObject(1);
            if (rs.next()) {
                role = new Role();
                role.setId(rs.getInt(1));
                role.setName(rs.getString(2));
            }
        } catch (SQLException e) {
            throw new RoleException(
                    "%%% Exception occured in RoleDAO getById() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
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
        return getById(id);
    }

    /**
     * Gets many entities from the database stored in the list.
     * Uses restrictions provided by hasXXXX() methods.
     * 
     * @param firstResult - index of first row starting from 1
     * @param maxResults - number of rows to be fetched
     * @return list of entities with specified restrictions
     * @throws RoleException if database problem occurs.
     */
    @Override
    public List<Role> getMany(int firstResult, int maxResults) throws RoleException {
        String query = queries.getProperty("HOSPITAL_ROLE_GET_MANY");
        Connection conToUse = null;
        CallableStatement cs = null;
        List<Role> roles = new ArrayList<>();
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            if (hasName != null) {
                cs.setString(2, hasName);
            } else {
                cs.setNull(2, OracleTypes.VARCHAR);
            }
            if (hasUser != null) {
                cs.setInt(3, hasUser.getId());
            } else {
                cs.setNull(3, OracleTypes.NUMBER);
            }
            cs.setInt(4, firstResult + maxResults);
            cs.setInt(5, firstResult);
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
            throw new RoleException(
                    "%%% Exception occured in RoleDAO getMany() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
        return roles;
    }

    /**
     * Gets many initialized entities from the database stored in the list.
     * Uses restrictions provided by hasXXXX() methods.
     * 
     * @param firstResult - index of first row starting from 1
     * @param maxResults - number of rows to be fetched
     * @return list of entities with specified restrictions
     * @throws RoleException if database problem occurs.
     */
    @Override
    public List<Role> getInitializedMany(int firstResult, int maxResults) throws RoleException {
        return getMany(firstResult, maxResults);
    }

    /**
     * Adds restriction for getMany() and getInitializedMany() methods.
     * 
     * @param name - restriction object
     * @return dao object with restriction
     */
    @Override
    public RoleDAO hasName(String name) {
        this.hasName = name;
        return this;
    }

    /**
     * Adds restriction for getMany() and getInitializedMany() methods.
     * 
     * @param user - restriction object
     * @return dao object with restriction
     */
    @Override
    public RoleDAO hasUser(User user) {
        this.hasUser = user;
        return this;
    }

}
