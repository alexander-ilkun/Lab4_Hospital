package com.ilkun.hospital.db.dao;

import com.ilkun.hospital.db.entity.Prescription;
import com.ilkun.hospital.exception.PrescriptionException;

/**
 * Customizes <tt>GenericDAO</tt> by entity, exception classes and restrictions for it.
 * 
 * @author alexander-ilkun
 */
public interface PrescriptionDAO extends GenericDAO<Prescription, PrescriptionException>, JdbcDAOSupport {
    
    /**
     * Adds restriction for getMany() and getInitializedMany() methods.
     * 
     * @param name - restriction object
     * @return dao object with restriction
     */
    PrescriptionDAO hasName(String name);

}
