package com.ilkun.hospital.db.dao.impl;

import com.ilkun.hospital.db.dao.ConcretePrescriptionDAO;
import com.ilkun.hospital.db.entity.ConcretePrescription;
import com.ilkun.hospital.db.entity.Patient;
import com.ilkun.hospital.db.entity.Prescription;
import com.ilkun.hospital.db.entity.User;
import com.ilkun.hospital.exception.ConcretePrescriptionException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import org.apache.commons.dbutils.DbUtils;

/**
 * This class represents <tt>ConcretePrescriptionDAO</tt> implementation.
 * Uses JDBC interface for database connection.
 *
 * @author alexander-ilkun
 */
public class ConcretePrescriptionJdbcDAOImpl extends JdbcDAOSupportImpl implements ConcretePrescriptionDAO {

    private Patient hasPatient;

    /**
     * Saves entity to the database.
     * 
     * @param entity - entity to save in database
     * @return new id of entity
     * @throws ConcretePrescriptionException if database problem occurs.
     */
    @Override
    public int save(ConcretePrescription entity) throws ConcretePrescriptionException {
        String query = queries.getProperty("HOSPITAL_CONPRESC_SAVE");
        Connection conToUse = null;
        CallableStatement cs = null;
        int status = 0;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setInt(1, entity.getPrescription().getId());
            cs.setInt(2, entity.getPatient().getId());
            if (entity.getPerformer() != null) {
                cs.setInt(3, entity.getPerformer().getId());
            } else {
                cs.setNull(3, OracleTypes.NUMBER);
            }
            cs.setString(4, entity.getDescription());
            cs.setInt(5, entity.getPerformed() ? 1 : 0);
            cs.registerOutParameter(6, OracleTypes.NUMBER);
            cs.executeUpdate();
            status = cs.getInt(6);
        } catch (SQLException e) {
            throw new ConcretePrescriptionException(
                    "%%% Exception occured in ConcretePrescriptionDAO save() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
        return status;
    }

    /**
     * Updates entity in the database.
     * 
     * @param entity - entity to update in database
     * @throws ConcretePrescriptionException if database problem occurs.
     */
    @Override
    public void update(ConcretePrescription entity) throws ConcretePrescriptionException {
        String query = queries.getProperty("HOSPITAL_CONPRESC_UPDATE");
        Connection conToUse = null;
        CallableStatement cs = null;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setInt(1, entity.getPrescription().getId());
            cs.setInt(2, entity.getPatient().getId());
            if (entity.getPerformer() != null) {
                cs.setInt(3, entity.getPerformer().getId());
            } else {
                cs.setNull(3, OracleTypes.NUMBER);
            }
            cs.setString(4, entity.getDescription());
            cs.setInt(5, entity.getPerformed() ? 1 : 0);
            cs.setInt(6, entity.getId());
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new ConcretePrescriptionException(
                    "%%% Exception occured in ConcretePrescriptionDAO update() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
    }

    /**
     * Deletes entity from the database.
     * 
     * @param entity - entity to delete from database
     * @throws ConcretePrescriptionException if database problem occurs.
     */
    @Override
    public void delete(ConcretePrescription entity) throws ConcretePrescriptionException {
        String query = queries.getProperty("HOSPITAL_CONPRESC_DELETE");
        Connection conToUse = null;
        CallableStatement cs = null;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setInt(1, entity.getId());
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new ConcretePrescriptionException(
                    "%%% Exception occured in ConcretePrescriptionDAO delete() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
    }

    /**
     * Gets entity from the database.
     * 
     * @param id - id of entity in database
     * @return entity with specified id
     * @throws ConcretePrescriptionException if database problem occurs.
     */
    @Override
    public ConcretePrescription getById(int id) throws ConcretePrescriptionException {
        String query = queries.getProperty("HOSPITAL_CONPRESC_GET_BY_ID");
        Connection conToUse = null;
        CallableStatement cs = null;
        ConcretePrescription concretePrescription = null;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setInt(2, id);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.executeQuery();
            ResultSet rs = (ResultSet) cs.getObject(1);
            if (rs.next()) {
                concretePrescription = new ConcretePrescription();
                concretePrescription.setId(rs.getInt(1));
                concretePrescription.setDescription(rs.getString(5));
                concretePrescription.setPerformed(rs.getInt(6) == 1);
            }
        } catch (SQLException e) {
            throw new ConcretePrescriptionException(
                    "%%% Exception occured in ConcretePrescriptionDAO getById() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
        return concretePrescription;
    }

    /**
     * Gets initialized entity from the database.
     * 
     * @param id - id of entity in database
     * @return entity with specified id
     * @throws ConcretePrescriptionException if database problem occurs.
     */
    @Override
    public ConcretePrescription getInitializedById(int id) throws ConcretePrescriptionException {
        ConcretePrescription concretePrescription = getById(id);
        if (concretePrescription != null) {
            initialize(concretePrescription);
        }
        return concretePrescription;
    }

    /**
     * Gets many entities from the database stored in the list.
     * Uses restrictions provided by hasXXXX() methods.
     * 
     * @param firstResult - index of first row starting from 1
     * @param maxResults - number of rows to be fetched
     * @return list of entities with specified restrictions
     * @throws ConcretePrescriptionException if database problem occurs.
     */
    @Override
    public List<ConcretePrescription> getMany(int firstResult, int maxResults) throws ConcretePrescriptionException {
        String query = queries.getProperty("HOSPITAL_CONPRESC_GET_MANY");
        Connection conToUse = null;
        CallableStatement cs = null;
        List<ConcretePrescription> concretePrescriptions = new ArrayList<>();
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            if (hasPatient != null) {
                cs.setInt(2, hasPatient.getId());
            } else {
                cs.setNull(2, OracleTypes.NUMBER);
            }
            cs.setInt(3, firstResult + maxResults);
            cs.setInt(4, firstResult);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.executeQuery();
            ResultSet rs = (ResultSet) cs.getObject(1);
            while (rs.next()) {
                ConcretePrescription concretePrescription = new ConcretePrescription();
                concretePrescription.setId(rs.getInt(1));
                concretePrescription.setDescription(rs.getString(5));
                concretePrescription.setPerformed(rs.getInt(6) == 1);
                concretePrescriptions.add(concretePrescription);
            }
        } catch (SQLException e) {
            throw new ConcretePrescriptionException(
                    "%%% Exception occured in ConcretePrescriptionDAO getMany() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
        return concretePrescriptions;
    }

    /**
     * Gets many initialized entities from the database stored in the list.
     * Uses restrictions provided by hasXXXX() methods.
     * 
     * @param firstResult - index of first row starting from 1
     * @param maxResults - number of rows to be fetched
     * @return list of entities with specified restrictions
     * @throws ConcretePrescriptionException if database problem occurs.
     */
    @Override
    public List<ConcretePrescription> getInitializedMany(int firstResult, int maxResults) throws ConcretePrescriptionException {
        List<ConcretePrescription> concretePrescriptions = getMany(firstResult, maxResults);
        for (ConcretePrescription concretePrescription : concretePrescriptions) {
            initialize(concretePrescription);
        }
        return concretePrescriptions;
    }

    /**
     * Adds restriction for getMany() and getInitializedMany() methods.
     * 
     * @param patient - restriction object
     * @return dao object with restriction
     */
    @Override
    public ConcretePrescriptionDAO hasPatient(Patient patient) {
        this.hasPatient = patient;
        return this;
    }

    /**
     * Initializes specified entity.
     * 
     * @param entity - entity to initialize from database
     * @return initialized entity
     * @throws ConcretePrescriptionException if database problem occurs.
     */
    private ConcretePrescription initialize(ConcretePrescription entity) throws ConcretePrescriptionException {
        String query = queries.getProperty("HOSPITAL_CONPRESC_INITIALIZE");
        Connection conToUse = null;
        CallableStatement cs = null;
        Prescription prescription = null;
        Patient patient = null;
        User performer = null;
        try {
            conToUse = getConnection();
            cs = conToUse.prepareCall(query);
            cs.setInt(2, entity.getId());
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.executeQuery();
            ResultSet rs = (ResultSet) cs.getObject(1);
            while (rs.next()) {
                prescription = new Prescription();
                prescription.setId(rs.getInt(4));
                prescription.setName(rs.getString(5));
                entity.setPrescription(prescription);
                patient = new Patient();
                patient.setId(rs.getInt(6));
                patient.setName(rs.getString(7));
                patient.setDiagnosis(rs.getString(8));
                patient.setDischarged(rs.getInt(9) == 1);
                entity.setPatient(patient);
                int userId = rs.getInt(10);
                if (!rs.wasNull()) {
                    performer = new User();
                    performer.setId(userId);
                    performer.setName(rs.getString(11));
                    entity.setPerformer(performer);
                }
            }
        } catch (SQLException e) {
            throw new ConcretePrescriptionException(
                    "%%% Exception occured in ConcretePrescriptionDAO initialize() %%% " + e);
        } finally {
            DbUtils.closeQuietly(cs);
        }
        return entity;
    }

}
