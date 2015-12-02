package com.ilkun.hospital.service.impl;

import com.ilkun.hospital.db.dao.ConcretePrescriptionDAO;
import com.ilkun.hospital.db.dao.PatientDAO;
import com.ilkun.hospital.db.entity.Patient;
import com.ilkun.hospital.db.util.ConnectionPoolUtil;
import com.ilkun.hospital.exception.ConcretePrescriptionException;
import com.ilkun.hospital.exception.GenericException;
import com.ilkun.hospital.exception.PatientException;
import com.ilkun.hospital.exception.ResourceHelperException;
import com.ilkun.hospital.factory.DAOFactory;
import com.ilkun.hospital.service.PatientService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 * This class represents <tt>PatientService</tt> implementation.
 * Uses dao layer for database operations.
 * 
 * @author alexander-ilkun
 */
public class PatientServiceImpl implements PatientService {

    /**
     * Saves entity to the database.
     * 
     * @param entity - entity to save in database
     * @throws PatientException if database problem occurs.
     */
    @Override
    public void save(Patient entity) throws PatientException {
        if (getById(entity.getId()) == null) {
            Connection conToUse = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                PatientDAO patientDAO = DAOFactory.getPatientDAO();
                ConcretePrescriptionDAO concretePrescriptionDAO = DAOFactory.getConcretePrescriptionDAO();
                patientDAO.setConnection(conToUse);
                concretePrescriptionDAO.setConnection(conToUse);

                // attaching the object
                int newId = patientDAO.save(entity);
                entity.setId(newId);

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | PatientException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new PatientException(e.getMessage());
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
     * @throws PatientException if database problem occurs.
     */
    @Override
    public void update(Patient entity) throws PatientException {
        if (getById(entity.getId()) != null) {
            Connection conToUse = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                PatientDAO patientDAO = DAOFactory.getPatientDAO();
                patientDAO.setConnection(conToUse);

                patientDAO.update(entity);

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new PatientException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
        } else {
            throw new PatientException("Patient object doesn't attached. Use save().");
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
        if (getById(entity.getId()) != null) {
            Connection conToUse = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                PatientDAO patientDAO = DAOFactory.getPatientDAO();
                patientDAO.setConnection(conToUse);

                patientDAO.delete(entity);

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new PatientException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
        } else {
            throw new PatientException("Patient object doesn't attached.");
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
        Connection conToUse = null;
        Patient patient = null;
        try {
            conToUse = ConnectionPoolUtil.getConnection();
            conToUse.setAutoCommit(false);
            PatientDAO patientDAO = DAOFactory.getPatientDAO();
            patientDAO.setConnection(conToUse);

            patient = patientDAO.getById(id);

            conToUse.commit();
        } catch (ResourceHelperException | SQLException | GenericException e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
            throw new PatientException(e.getMessage());
        } finally {
            ConnectionPoolUtil.closeConnection(conToUse);
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
        Connection conToUse = null;
        Patient patient = null;
        try {
            conToUse = ConnectionPoolUtil.getConnection();
            conToUse.setAutoCommit(false);
            PatientDAO patientDAO = DAOFactory.getPatientDAO();
            ConcretePrescriptionDAO concretePrescriptionDAO = DAOFactory.getConcretePrescriptionDAO();
            patientDAO.setConnection(conToUse);
            concretePrescriptionDAO.setConnection(conToUse);

            patient = patientDAO.getInitializedById(id);
            patient.setConcretePrescriptions(concretePrescriptionDAO.hasPatient(patient).getInitializedMany(1, Integer.MAX_VALUE));

            conToUse.commit();
        } catch (ResourceHelperException | SQLException | GenericException e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
            throw new PatientException(e.getMessage());
        } finally {
            ConnectionPoolUtil.closeConnection(conToUse);
        }
        return patient;
    }

    /**
     * Returns query object for the service.
     * 
     * @return query object for the service
     */
    @Override
    public PatientQueryImpl query() {
        return new PatientQueryImpl();
    }

    /**
    * This class represents <tt>GenericQuery</tt> implementation.
    * Uses dao layer for database operations.
    * 
    * @author alexander-ilkun
    */
    public class PatientQueryImpl implements PatientQuery {

        private final PatientDAO patientDAO = DAOFactory.getPatientDAO();

        private PatientQueryImpl() {
        }

        /**
        * Gets many entities stored in the list.
        * Uses restrictions provided by hasXXXX() methods.
        * 
        * @param firstResult - index of first row starting from 1
        * @param maxResults - number of rows to be fetched
        * @return list of entities with specified restrictions
        * @throws PatientException if application problem occurs.
        */
        @Override
        public List<Patient> find(int firstResult, int maxResults) throws PatientException {
            Connection conToUse = null;
            List<Patient> patients = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                ConcretePrescriptionDAO concretePrescriptionDAO = DAOFactory.getConcretePrescriptionDAO();
                patientDAO.setConnection(conToUse);
                concretePrescriptionDAO.setConnection(conToUse);

                patients = patientDAO.getMany(firstResult, maxResults);
                for (Patient patient : patients) {
                    patient.setConcretePrescriptions(concretePrescriptionDAO.hasPatient(patient).getInitializedMany(1, Integer.MAX_VALUE));
                }

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new PatientException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
            return patients;
        }

        /**
        * Gets many initialized entities stored in the list.
        * Uses restrictions provided by hasXXXX() methods.
        * 
        * @param firstResult - index of first row starting from 1
        * @param maxResults - number of rows to be fetched
        * @return list of entities with specified restrictions
        * @throws PatientException if application problem occurs.
        */
        @Override
        public List<Patient> findInitialized(int firstResult, int maxResults) throws PatientException {
            Connection conToUse = null;
            List<Patient> patients = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                patientDAO.setConnection(conToUse);

                patients = patientDAO.getInitializedMany(firstResult, maxResults);

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new PatientException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
            return patients;
        }

        
        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param name - restriction object
        * @return query object with restriction
        */
        @Override
        public PatientQuery hasName(String name) {
            patientDAO.hasName(name);
            return this;
        }

        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param discharged - restriction object
        * @return query object with restriction
        */
        @Override
        public PatientQuery hasDischarged(boolean discharged) {
            patientDAO.hasDischarged(discharged);
            return this;
        }

    }

}
