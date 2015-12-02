package com.ilkun.hospital.db.dao.impl;

import com.ilkun.hospital.db.dao.PatientDAO;
import com.ilkun.hospital.db.entity.Patient;
import com.ilkun.hospital.exception.PatientException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import org.apache.commons.dbutils.DbUtils;

/**
 * This class represents <tt>PatientDAO</tt> implementation.
 * Uses JDBC interface for database connection.
 *
 * @author alexander-ilkun
 */
public class PatientJdbcDAOImpl extends JdbcDAOSupportImpl implements PatientDAO {

    private String hasName;
    private Integer hasDischarged;

    /**
     * Saves entity to the database.
     * 
     * @param entity - entity to save in database
     * @return new id of entity
     * @throws PatientException if database problem occurs.
     */
    @Override
    public int save(Patient entity) throws PatientException {
        String query = queries.getProperty("HOSPITAL_PATIENT_SAVE");
        Connection conToUse = null;
        CallableStatement cs = null;
        int status = 0;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setString(1, entity.getName());
            cs.setString(2, entity.getDiagnosis());
            cs.setInt(3, entity.getDischarged() ? 1 : 0);
            cs.registerOutParameter(4, OracleTypes.NUMBER);
            cs.executeUpdate();
            status = cs.getInt(4);
        } catch (SQLException e) {
            throw new PatientException(
                    "%%% Exception occured in PatientDAO save() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
        return status;
    }

    /**
     * Updates entity in the database.
     * 
     * @param entity - entity to update in database
     * @throws PatientException if database problem occurs.
     */
    @Override
    public void update(Patient entity) throws PatientException {
        String query = queries.getProperty("HOSPITAL_PATIENT_UPDATE");
        Connection conToUse = null;
        CallableStatement cs = null;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setString(1, entity.getName());
            cs.setString(2, entity.getDiagnosis());
            cs.setInt(3, entity.getDischarged() ? 1 : 0);
            cs.setInt(4, entity.getId());
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new PatientException(
                    "%%% Exception occured in PatientDAO update() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
    }

    /**
     * Deletes entity from the database.
     * 
     * @param entity - entity to delete from database
     * @throws PatientException if database problem occurs.
     */
    @Override
    public void delete(Patient entity) throws PatientException {
        String query = queries.getProperty("HOSPITAL_PATIENT_DELETE");
        Connection conToUse = null;
        CallableStatement cs = null;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setInt(1, entity.getId());
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new PatientException(
                    "%%% Exception occured in PatientDAO delete() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
    }

    /**
     * Gets entity from the database.
     * 
     * @param id - id of entity in database
     * @return entity with specified id
     * @throws PatientException if database problem occurs.
     */
    @Override
    public Patient getById(int id) throws PatientException {
        String query = queries.getProperty("HOSPITAL_PATIENT_GET_BY_ID");
        Connection conToUse = null;
        CallableStatement cs = null;
        Patient patient = null;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setInt(2, id);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.executeQuery();
            ResultSet rs = (ResultSet) cs.getObject(1);
            if (rs.next()) {
                patient = new Patient();
                patient.setId(rs.getInt(1));
                patient.setName(rs.getString(2));
                patient.setDiagnosis(rs.getString(3));
                patient.setDischarged(rs.getInt(4) == 1);
            }
        } catch (SQLException e) {
            throw new PatientException(
                    "%%% Exception occured in PatientDAO getById() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
        return patient;
    }

    /**
     * Gets initialized entity from the database.
     * 
     * @param id - id of entity in database
     * @return entity with specified id
     * @throws PatientException if database problem occurs.
     */
    @Override
    public Patient getInitializedById(int id) throws PatientException {
        return getById(id);
    }

    /**
     * Gets many entities from the database stored in the list.
     * Uses restrictions provided by hasXXXX() methods.
     * 
     * @param firstResult - index of first row starting from 1
     * @param maxResults - number of rows to be fetched
     * @return list of entities with specified restrictions
     * @throws PatientException if database problem occurs.
     */
    @Override
    public List<Patient> getMany(int firstResult, int maxResults) throws PatientException {
        String query = queries.getProperty("HOSPITAL_PATIENT_GET_MANY");
        Connection conToUse = null;
        CallableStatement cs = null;
        List<Patient> patients = new ArrayList<>();
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            if (hasName != null) {
                cs.setString(2, hasName);
            } else {
                cs.setNull(2, OracleTypes.VARCHAR);
            }
            if (hasDischarged != null) {
                cs.setInt(3, hasDischarged);
            } else {
                cs.setNull(3, OracleTypes.NUMBER);
            }
            cs.setInt(4, firstResult + maxResults);
            cs.setInt(5, firstResult);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.executeQuery();
            ResultSet rs = (ResultSet) cs.getObject(1);
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setId(rs.getInt(1));
                patient.setName(rs.getString(2));
                patient.setDiagnosis(rs.getString(3));
                patient.setDischarged(rs.getInt(4) == 1);
                patients.add(patient);
            }
        } catch (SQLException e) {
            throw new PatientException(
                    "%%% Exception occured in PatientDAO getMany() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
        return patients;
    }

    /**
     * Gets many initialized entities from the database stored in the list.
     * Uses restrictions provided by hasXXXX() methods.
     * 
     * @param firstResult - index of first row starting from 1
     * @param maxResults - number of rows to be fetched
     * @return list of entities with specified restrictions
     * @throws PatientException if database problem occurs.
     */
    @Override
    public List<Patient> getInitializedMany(int firstResult, int maxResults) throws PatientException {
        return getMany(firstResult, maxResults);
    }

    @Override
    public PatientDAO hasName(String name) {
        this.hasName = name;
        return this;
    }

    @Override
    public PatientDAO hasDischarged(boolean discharged) {
        this.hasDischarged = discharged ? 1 : null;
        return this;
    }

}
