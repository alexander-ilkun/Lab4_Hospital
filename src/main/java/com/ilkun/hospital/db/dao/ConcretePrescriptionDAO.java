package com.ilkun.hospital.db.dao;

import com.ilkun.hospital.db.entity.ConcretePrescription;
import com.ilkun.hospital.db.entity.Patient;
import com.ilkun.hospital.exception.ConcretePrescriptionException;

/**
 * Customizes <tt>GenericDAO</tt> by entity, exception classes and restrictions for it.
 * 
 * @author alexander-ilkun
 */
public interface ConcretePrescriptionDAO extends GenericDAO<ConcretePrescription, ConcretePrescriptionException>, JdbcDAOSupport {
    
    /**
     * Adds restriction for getMany() and getInitializedMany() methods.
     * 
     * @param patient - restriction object
     * @return dao object with restriction
     */
    ConcretePrescriptionDAO hasPatient(Patient patient);

}
