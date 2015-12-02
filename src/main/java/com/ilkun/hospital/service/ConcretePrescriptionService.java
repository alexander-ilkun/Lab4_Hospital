package com.ilkun.hospital.service;

import com.ilkun.hospital.db.entity.ConcretePrescription;
import com.ilkun.hospital.db.entity.Patient;
import com.ilkun.hospital.exception.ConcretePrescriptionException;

/**
 * Customizes <tt>GenericService</tt> by entity, exception classes and 
 * custom query interface.
 * 
 * @author alexander-ilkun
 */
public interface ConcretePrescriptionService extends GenericService<ConcretePrescription, ConcretePrescriptionException> {

    /**
     * Returns query object for the service.
     * 
     * @return query object for the service
     */
    @Override
    ConcretePrescriptionQuery query();
    
    /**
    * Customizes <tt>GenericQuery</tt> by entity, exception classes and 
    * custom restrictions on query.
    * 
    * @author alexander-ilkun
    */
    public interface ConcretePrescriptionQuery extends GenericQuery<ConcretePrescription, ConcretePrescriptionException> {

        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param patient - restriction object
        * @return query object with restriction
        */
        ConcretePrescriptionQuery hasPatient(Patient patient);
    
    }

}
