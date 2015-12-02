package com.ilkun.hospital.db.dao;

import com.ilkun.hospital.db.entity.Patient;
import com.ilkun.hospital.exception.PatientException;

/**
 * Customizes <tt>GenericDAO</tt> by entity, exception classes and restrictions for it.
 * 
 * @author alexander-ilkun
 */
public interface PatientDAO extends GenericDAO<Patient, PatientException>, JdbcDAOSupport {
    
    /**
     * Adds restriction for getMany() and getInitializedMany() methods.
     * 
     * @param name - restriction object
     * @return dao object with restriction
     */
    PatientDAO hasName(String name);

    /**
     * Adds restriction for getMany() and getInitializedMany() methods.
     * 
     * @param discharged - restriction object
     * @return dao object with restriction
     */
    PatientDAO hasDischarged(boolean discharged);

}
