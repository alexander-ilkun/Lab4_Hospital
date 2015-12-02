package com.ilkun.hospital.service.impl;

import com.ilkun.hospital.db.dao.PrescriptionDAO;
import com.ilkun.hospital.db.entity.Prescription;
import com.ilkun.hospital.db.util.ConnectionPoolUtil;
import com.ilkun.hospital.exception.GenericException;
import com.ilkun.hospital.exception.PrescriptionException;
import com.ilkun.hospital.exception.ResourceHelperException;
import com.ilkun.hospital.factory.DAOFactory;
import com.ilkun.hospital.service.PrescriptionService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 * This class represents <tt>PrescriptionService</tt> implementation.
 * Uses dao layer for database operations.
 * 
 * @author alexander-ilkun
 */
public class PrescriptionServiceImpl implements PrescriptionService {

    /**
     * Saves entity to the database.
     * 
     * @param entity - entity to save in database
     * @throws PrescriptionException if database problem occurs.
     */
    @Override
    public void save(Prescription entity) throws PrescriptionException {
        if (getById(entity.getId()) == null) {
            Connection conToUse = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                PrescriptionDAO prescriptionDAO = DAOFactory.getPrescriptionDAO();
                prescriptionDAO.setConnection(conToUse);

                // attaching the object
                int newId = prescriptionDAO.save(entity);
                entity.setId(newId);

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | PrescriptionException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new PrescriptionException(e.getMessage());
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
     * @throws PrescriptionException if database problem occurs.
     */
    @Override
    public void update(Prescription entity) throws PrescriptionException {
        if (getById(entity.getId()) != null) {
            Connection conToUse = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                PrescriptionDAO prescriptionDAO = DAOFactory.getPrescriptionDAO();
                prescriptionDAO.setConnection(conToUse);

                prescriptionDAO.update(entity);

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new PrescriptionException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
        } else {
            throw new PrescriptionException("Prescription object doesn't attached. Use save().");
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
        if (getById(entity.getId()) != null) {
            Connection conToUse = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                PrescriptionDAO prescriptionDAO = DAOFactory.getPrescriptionDAO();
                prescriptionDAO.setConnection(conToUse);

                prescriptionDAO.delete(entity);

                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new PrescriptionException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
        } else {
            throw new PrescriptionException("Prescription object doesn't attached.");
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
        Connection conToUse = null;
        Prescription prescription = null;
        try {
            conToUse = ConnectionPoolUtil.getConnection();
            conToUse.setAutoCommit(false);
            PrescriptionDAO prescriptionDAO = DAOFactory.getPrescriptionDAO();
            prescriptionDAO.setConnection(conToUse);
            
            prescription = prescriptionDAO.getById(id);
            
            conToUse.commit();
        } catch (ResourceHelperException | SQLException | GenericException e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
            throw new PrescriptionException(e.getMessage());
        } finally {
            ConnectionPoolUtil.closeConnection(conToUse);
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
        Connection conToUse = null;
        Prescription prescription = null;
        try {
            conToUse = ConnectionPoolUtil.getConnection();
            conToUse.setAutoCommit(false);
            PrescriptionDAO prescriptionDAO = DAOFactory.getPrescriptionDAO();
            prescriptionDAO.setConnection(conToUse);
            
            prescription = prescriptionDAO.getInitializedById(id);
            
            conToUse.commit();
        } catch (ResourceHelperException | SQLException | GenericException e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
            throw new PrescriptionException(e.getMessage());
        } finally {
            ConnectionPoolUtil.closeConnection(conToUse);
        }
        return prescription;
    }

    /**
     * Returns query object for the service.
     * 
     * @return query object for the service
     */
    @Override
    public PrescriptionQueryImpl query() {
        return new PrescriptionQueryImpl();
    }

    /**
    * This class represents <tt>GenericQuery</tt> implementation.
    * Uses dao layer for database operations.
    * 
    * @author alexander-ilkun
    */
    public class PrescriptionQueryImpl implements PrescriptionQuery {

        private final PrescriptionDAO prescriptionDAO = DAOFactory.getPrescriptionDAO();

        private PrescriptionQueryImpl() {
        }

        /**
        * Gets many entities stored in the list.
        * Uses restrictions provided by hasXXXX() methods.
        * 
        * @param firstResult - index of first row starting from 1
        * @param maxResults - number of rows to be fetched
        * @return list of entities with specified restrictions
        * @throws PrescriptionException if application problem occurs.
        */
        @Override
        public List<Prescription> find(int firstResult, int maxResults) throws PrescriptionException {
            Connection conToUse = null;
            List<Prescription> prescriptions = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                prescriptionDAO.setConnection(conToUse);
                prescriptions = prescriptionDAO.getMany(firstResult, maxResults);
                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new PrescriptionException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
            return prescriptions;
        }

        /**
        * Gets many initialized entities stored in the list.
        * Uses restrictions provided by hasXXXX() methods.
        * 
        * @param firstResult - index of first row starting from 1
        * @param maxResults - number of rows to be fetched
        * @return list of entities with specified restrictions
        * @throws PrescriptionException if application problem occurs.
        */
        @Override
        public List<Prescription> findInitialized(int firstResult, int maxResults) throws PrescriptionException {
            Connection conToUse = null;
            List<Prescription> prescriptions = null;
            try {
                conToUse = ConnectionPoolUtil.getConnection();
                conToUse.setAutoCommit(false);
                prescriptionDAO.setConnection(conToUse);
                
                prescriptions = prescriptionDAO.getInitializedMany(firstResult, maxResults);
                
                conToUse.commit();
            } catch (ResourceHelperException | SQLException | GenericException e) {
                DbUtils.rollbackAndCloseQuietly(conToUse);
                throw new PrescriptionException(e.getMessage());
            } finally {
                ConnectionPoolUtil.closeConnection(conToUse);
            }
            return prescriptions;
        }

        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param name - restriction object
        * @return query object with restriction
        */
        @Override
        public PrescriptionQuery hasName(String name) {
            prescriptionDAO.hasName(name);
            return this;
        }

    }

}
