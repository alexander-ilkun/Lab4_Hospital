package com.ilkun.hospital.service.impl;

import com.ilkun.hospital.db.dao.ConcretePrescriptionDAO;
import com.ilkun.hospital.db.dao.PatientDAO;
import com.ilkun.hospital.db.dao.PrescriptionDAO;
import com.ilkun.hospital.db.entity.ConcretePrescription;
import com.ilkun.hospital.db.entity.Patient;
import com.ilkun.hospital.db.util.ConnectionPoolUtil;
import com.ilkun.hospital.exception.ConcretePrescriptionException;
import com.ilkun.hospital.exception.GenericException;
import com.ilkun.hospital.exception.PatientException;
import com.ilkun.hospital.exception.PrescriptionException;
import com.ilkun.hospital.exception.ResourceHelperException;
import com.ilkun.hospital.factory.DAOFactory;
import com.ilkun.hospital.service.ConcretePrescriptionService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 * This class represents <tt>ConcretePrescriptionService</tt> implementation.
 * Uses dao layer for database operations.
 * 
 * @author alexander-ilkun
 */
public class ConcretePrescriptionServiceImpl implements ConcretePrescriptionService {

    /**
     * Saves entity to the database.
     * 
     * @param entity - entity to save in database
     * @throws ConcretePrescriptionException if database problem occurs.
     */
    @Override
    public void save(ConcretePrescription entity) throws ConcretePrescriptionException {
        if (getById(entity.getId()) == null) {
            Connection conToUse = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                ConcretePrescriptionDAO concretePrescriptionDAO = DAOFactory.getConcretePrescriptionDAO();
                PatientDAO patientDAO = DAOFactory.getPatientDAO();
                PrescriptionDAO prescriptionDAO = DAOFactory.getPrescriptionDAO();
                concretePrescriptionDAO.setConnection(conToUse);
                patientDAO.setConnection(conToUse);
                prescriptionDAO.setConnection(conToUse);
                
                // attaching the object
                int newId = 0;
                if (patientDAO.getById(entity.getPatient().getId()) != null) {
                    if (prescriptionDAO.getById(entity.getPrescription().getId()) != null) {
                        newId = concretePrescriptionDAO.save(entity);
                    } else {
                        throw new ConcretePrescriptionException("Prescription object doesn't attached");
                    }
                } else {
                    throw new ConcretePrescriptionException("Patient object doesn't attached");
                }
                entity.setId(newId);

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | PatientException | PrescriptionException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new ConcretePrescriptionException(e.getMessage());
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
     * @throws ConcretePrescriptionException if database problem occurs.
     */
    @Override
    public void update(ConcretePrescription entity) throws ConcretePrescriptionException {
        if (getById(entity.getId()) != null) {
            Connection conToUse = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                ConcretePrescriptionDAO concretePrescriptionDAO = DAOFactory.getConcretePrescriptionDAO();
                PatientDAO patientDAO = DAOFactory.getPatientDAO();
                PrescriptionDAO prescriptionDAO = DAOFactory.getPrescriptionDAO();
                concretePrescriptionDAO.setConnection(conToUse);
                patientDAO.setConnection(conToUse);
                prescriptionDAO.setConnection(conToUse);
                
                // attaching the object
                if (patientDAO.getById(entity.getPatient().getId()) != null) {
                    if (prescriptionDAO.getById(entity.getPrescription().getId()) != null) {
                        concretePrescriptionDAO.update(entity);
                    } else {
                        throw new ConcretePrescriptionException("Prescription object doesn't attached");
                    }
                } else {
                    throw new ConcretePrescriptionException("Patient object doesn't attached");
                }

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new ConcretePrescriptionException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
        } else {
            throw new ConcretePrescriptionException("ConcretePrescription object doesn't attached. Use save().");
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
        if (getById(entity.getId()) != null) {
            Connection conToUse = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                ConcretePrescriptionDAO concretePrescriptionDAO = DAOFactory.getConcretePrescriptionDAO();

                concretePrescriptionDAO.delete(entity);

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new ConcretePrescriptionException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
        } else {
            throw new ConcretePrescriptionException("ConcretePrescription object doesn't attached.");
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
        Connection conToUse = null;
        ConcretePrescription concretePrescription = null;
        try {
            conToUse = ConnectionPoolUtil.getConnection();
            conToUse.setAutoCommit(false);
            ConcretePrescriptionDAO concretePrescriptionDAO = DAOFactory.getConcretePrescriptionDAO();
            concretePrescriptionDAO.setConnection(conToUse);
            
            concretePrescription = concretePrescriptionDAO.getById(id);
            
            conToUse.commit();
        } catch (ResourceHelperException | SQLException | GenericException e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
            throw new ConcretePrescriptionException(e.getMessage());
        } finally {
            ConnectionPoolUtil.closeConnection(conToUse);
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
        Connection conToUse = null;
        ConcretePrescription concretePrescription = null;
        try {
            conToUse = ConnectionPoolUtil.getConnection();
            conToUse.setAutoCommit(false);
            ConcretePrescriptionDAO concretePrescriptionDAO = DAOFactory.getConcretePrescriptionDAO();
            concretePrescriptionDAO.setConnection(conToUse);
            
            concretePrescription = concretePrescriptionDAO.getInitializedById(id);

            conToUse.commit();
        } catch (ResourceHelperException | SQLException | GenericException e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
            throw new ConcretePrescriptionException(e.getMessage());
        } finally {
            ConnectionPoolUtil.closeConnection(conToUse);
        }
        return concretePrescription;
    }

    /**
     * Returns query object for the service.
     * 
     * @return query object for the service
     */
    @Override
    public ConcretePrescriptionQueryImpl query() {
        return new ConcretePrescriptionQueryImpl();
    }

    /**
    * This class represents <tt>GenericQuery</tt> implementation.
    * Uses dao layer for database operations.
    * 
    * @author alexander-ilkun
    */
    public class ConcretePrescriptionQueryImpl implements ConcretePrescriptionQuery {

        private final ConcretePrescriptionDAO concretePrescriptionDAO = DAOFactory.getConcretePrescriptionDAO();

        private ConcretePrescriptionQueryImpl() {
        }

        /**
        * Gets many entities stored in the list.
        * Uses restrictions provided by hasXXXX() methods.
        * 
        * @param firstResult - index of first row starting from 1
        * @param maxResults - number of rows to be fetched
        * @return list of entities with specified restrictions
        * @throws ConcretePrescriptionException if application problem occurs.
        */
        @Override
        public List<ConcretePrescription> find(int firstResult, int maxResults) throws ConcretePrescriptionException {
            Connection conToUse = null;
            List<ConcretePrescription> concretePrescriptions = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                concretePrescriptionDAO.setConnection(conToUse);

                concretePrescriptions = concretePrescriptionDAO.getMany(firstResult, maxResults);

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new ConcretePrescriptionException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
            return concretePrescriptions;
        }

        /**
        * Gets many initialized entities stored in the list.
        * Uses restrictions provided by hasXXXX() methods.
        * 
        * @param firstResult - index of first row starting from 1
        * @param maxResults - number of rows to be fetched
        * @return list of entities with specified restrictions
        * @throws ConcretePrescriptionException if application problem occurs.
        */
        @Override
        public List<ConcretePrescription> findInitialized(int firstResult, int maxResults) throws ConcretePrescriptionException {
            Connection conToUse = null;
            List<ConcretePrescription> concretePrescriptions = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                concretePrescriptionDAO.setConnection(conToUse);

                concretePrescriptions = concretePrescriptionDAO.getInitializedMany(firstResult, maxResults);

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new ConcretePrescriptionException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
            return concretePrescriptions;
        }

        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param patient - restriction object
        * @return query object with restriction
        */
        @Override
        public ConcretePrescriptionQuery hasPatient(Patient patient) {
            concretePrescriptionDAO.hasPatient(patient);
            return this;
        }

    }

}
