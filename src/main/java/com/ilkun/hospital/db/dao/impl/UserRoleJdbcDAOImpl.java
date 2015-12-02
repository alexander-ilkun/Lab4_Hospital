package com.ilkun.hospital.db.dao.impl;

import com.ilkun.hospital.db.entity.UserRole;
import com.ilkun.hospital.db.dao.UserRoleDAO;
import com.ilkun.hospital.exception.UserRoleException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author alexander-ilkun
 */
public class UserRoleJdbcDAOImpl extends JdbcDAOSupportImpl implements UserRoleDAO {

    @Override
    public int save(UserRole entity) throws UserRoleException {
        String query = queries.getProperty("HOSPITAL_USER_ROLE_SAVE");
        Connection conToUse = null;
        CallableStatement cs = null;
        int status = 0;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setInt(1, entity.getUserId());
            cs.setInt(2, entity.getRoleId());
            status = cs.executeUpdate();
        } catch (SQLException e) {
            throw new UserRoleException(
                    "%%% Exception occured in UserRoleDao save() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
        return status;
    }

    @Override
    public void update(UserRole entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(UserRole entity) throws UserRoleException {
        String userQuery = queries.getProperty("HOSPITAL_USER_ROLE_DELETE_BY_USER");
        String roleQuery = queries.getProperty("HOSPITAL_USER_ROLE_DELETE_BY_ROLE");
        Connection conToUse = null;
        CallableStatement cs = null;
        try {
            conToUse = getConnection();
            if (entity.getUserId() != 0) {
                cs = conToUse.prepareCall(userQuery);
                cs.setInt(1, entity.getUserId());
                cs.executeUpdate();
            } else if (entity.getRoleId() != 0) {
                cs = conToUse.prepareCall(roleQuery);
                cs.setInt(1, entity.getRoleId());
                cs.executeUpdate();
            }
        } catch (SQLException e) {
            throw new UserRoleException(
                    "%%% Exception occured in EmployeeDao save() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
    }

    @Override
    public UserRole getById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<UserRole> getMany(int firstResult, int maxResults) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<UserRole> getInitializedMany(int firstResult, int maxResults) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UserRole getInitializedById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
