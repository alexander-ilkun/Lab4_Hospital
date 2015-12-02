package com.ilkun.hospital.service;

import com.ilkun.hospital.db.entity.Patient;
import com.ilkun.hospital.exception.PatientException;

/**
 * Customizes <tt>GenericService</tt> by entity, exception classes and 
 * custom query interface.
 * 
 * @author alexander-ilkun
 */
public interface PatientService extends GenericService<Patient, PatientException> {

    /**
     * Returns query object for the service.
     * 
     * @return query object for the service
     */
    @Override
    PatientQuery query();
    
    /**
    * Customizes <tt>GenericQuery</tt> by entity, exception classes and 
    * custom restrictions on query.
    * 
    * @author alexander-ilkun
    */
    public interface PatientQuery extends GenericQuery<Patient, PatientException> {

        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param name - restriction object
        * @return query object with restriction
        */
        PatientQuery hasName(String name);
    
        /**
        * Adds restriction for find() and findInitialized() methods.
        * 
        * @param discharged - restriction object
        * @return query object with restriction
        */
        PatientQuery hasDischarged(boolean discharged);
    
    }

}
