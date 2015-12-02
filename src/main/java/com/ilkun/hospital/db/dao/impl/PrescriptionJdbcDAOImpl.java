package com.ilkun.hospital.db.dao.impl;

import com.ilkun.hospital.db.dao.PrescriptionDAO;
import com.ilkun.hospital.db.entity.Prescription;
import com.ilkun.hospital.exception.PrescriptionException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import org.apache.commons.dbutils.DbUtils;

/**
 * This class represents <tt>PrescriptionDAO</tt> implementation.
 * Uses JDBC interface for database connection.
 *
 * @author alexander-ilkun
 */
public class PrescriptionJdbcDAOImpl extends JdbcDAOSupportImpl implements PrescriptionDAO {

    private String hasName;

    /**
     * Saves entity to the database.
     * 
     * @param entity - entity to save in database
     * @return new id of entity
     * @throws PrescriptionException if database problem occurs.
     */
    @Override
    public int save(Prescription entity) throws PrescriptionException {
        String query = queries.getProperty("HOSPITAL_PRESCRIPTION_SAVE");
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
            throw new PrescriptionException(
                    "%%% Exception occured in PrescriptionDAO save() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
        return status;
    }

    /**
     * Updates entity in the database.
     * 
     * @param entity - entity to update in database
     * @throws PrescriptionException if database problem occurs.
     */
    @Override
    public void update(Prescription entity) throws PrescriptionException {
        String query = queries.getProperty("HOSPITAL_PRESCRIPTION_UPDATE");
        Connection conToUse = null;
        CallableStatement cs = null;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setString(1, entity.getName());
            cs.setInt(2, entity.getId());
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new PrescriptionException(
                    "%%% Exception occured in PrescriptionDAO update() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
    }

    /**
     * Deletes entity from the database.
     * 
     * @param entity - entity to delete from database
     * @throws PrescriptionException if database problem occurs.
     */
    @Override
    public void delete(Prescription entity) throws PrescriptionException {
        String query = queries.getProperty("HOSPITAL_PRESCRIPTION_DELETE");
        Connection conToUse = null;
        CallableStatement cs = null;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setInt(1, entity.getId());
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new PrescriptionException(
                    "%%% Exception occured in PrescriptionDAO delete() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
    }

    /**
     * Gets entity from the database.
     * 
     * @param id - id of entity in database
     * @return entity with specified id
     * @throws PrescriptionException if database problem occurs.
     */
    @Override
    public Prescription getById(int id) throws PrescriptionException {
        String query = queries.getProperty("HOSPITAL_PRESCRIPTION_GET_BY_ID");
        Connection conToUse = null;
        CallableStatement cs = null;
        Prescription prescription = null;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setInt(2, id);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.executeQuery();
            ResultSet rs = (ResultSet) cs.getObject(1);
            if (rs.next()) {
                prescription = new Prescription();
                prescription.setId(rs.getInt(1));
                prescription.setName(rs.getString(2));
            }
        } catch (SQLException e) {
            throw new PrescriptionException(
                    "%%% Exception occured in PrescriptionDAO getById() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
        return prescription;
    }

    /**
     * Gets initialized entity from the database.
     * 
     * @param id - id of entity in database
     * @return entity with specified id
     * @throws PrescriptionException if database problem occurs.
     */
    @Override
    public Prescription getInitializedById(int id) throws PrescriptionException {
        return getById(id);
    }

    /**
     * Gets many entities from the database stored in the list.
     * Uses restrictions provided by hasXXXX() methods.
     * 
     * @param firstResult - index of first row starting from 1
     * @param maxResults - number of rows to be fetched
     * @return list of entities with specified restrictions
     * @throws PrescriptionException if database problem occurs.
     */
    @Override
    public List<Prescription> getMany(int firstResult, int maxResults) throws PrescriptionException {
        String query = queries.getProperty("HOSPITAL_PRESCRIPTION_GET_MANY");
        Connection conToUse = null;
        CallableStatement cs = null;
        List<Prescription> prescriptions = new ArrayList<>();
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            if (hasName != null) {
                cs.setString(2, hasName);
            } else {
                cs.setNull(2, OracleTypes.VARCHAR);
            }
            cs.setInt(3, firstResult + maxResults);
            cs.setInt(4, firstResult);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.executeQuery();
            ResultSet rs = (ResultSet) cs.getObject(1);
            while (rs.next()) {
                Prescription prescription = new Prescription();
                prescription.setId(rs.getInt(1));
                prescription.setName(rs.getString(2));
                prescriptions.add(prescription);
            }
        } catch (SQLException e) {
            throw new PrescriptionException(
                    "%%% Exception occured in PrescriptionDAO getMany() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
        return prescriptions;
    }

    /**
     * Gets many initialized entities from the database stored in the list.
     * Uses restrictions provided by hasXXXX() methods.
     * 
     * @param firstResult - index of first row starting from 1
     * @param maxResults - number of rows to be fetched
     * @return list of entities with specified restrictions
     * @throws PrescriptionException if database problem occurs.
     */
    @Override
    public List<Prescription> getInitializedMany(int firstResult, int maxResults) throws PrescriptionException {
        return getMany(firstResult, maxResults);
    }

    /**
     * Adds restriction for getMany() and getInitializedMany() methods.
     * 
     * @param name - restriction object
     * @return dao object with restriction
     */
    @Override
    public PrescriptionDAO hasName(String name) {
        this.hasName = name;
        return this;
    }

}
