package com.ilkun.hospital.service;

import com.ilkun.hospital.db.entity.Prescription;
import com.ilkun.hospital.exception.PrescriptionException;

/**
 * Customizes <tt>GenericService</tt> by entity, exception classes and 
 * custom query interface.
 * 
 * @author alexander-ilkun
 */
public interface PrescriptionService extends GenericService<Prescription, PrescriptionException> {

    /**
     * Returns query object for the service.
     * 
     * @return query object for the service
     */
    @Override
    PrescriptionQuery query();

    /**
    * Customizes <tt>GenericQuery</tt> by entity, exception classes and 
    * custom restrictions on query.
    * 
    * @author alexander-ilkun
    */
    public interface PrescriptionQuery extends GenericQuery<Prescription, PrescriptionException> {

        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param name - restriction object
        * @return query object with restriction
        */
        PrescriptionQuery hasName(String name);

    }

}
