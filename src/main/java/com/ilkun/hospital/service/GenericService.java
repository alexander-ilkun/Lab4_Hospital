package com.ilkun.hospital.service;

import com.ilkun.hospital.exception.GenericException;
import java.util.List;

/**
 * This interface provides transaction layer.
 * It encapsulates work with DAO layer.
 *
 * @author alexander-ilkun
 * @param <T> - class of the entity
 * @param <E> - application depended exception
 */
public interface GenericService<T, E extends GenericException> {

    /**
     * Saves entity.
     * 
     * @param entity - entity to save
     * @throws E if application exception occurs.
     */
    void save(T entity) throws E;

    /**
     * Updates entity.
     * 
     * @param entity - entity to save
     * @throws E if application exception occurs.
     */
    void update(T entity) throws E;

    /**
     * Deletes entity.
     * 
     * @param entity - entity to save
     * @throws E if application exception occurs.
     */
    void delete(T entity) throws E;

    /**
     * Gets entity.
     * 
     * @param id - id of entity in database
     * @return entity with specified id
     * @throws E if application problem occurs.
     */
    T getById(int id) throws E;

    /**
     * Gets initialized entity.
     * 
     * @param id - id of entity
     * @return entity with specified id
     * @throws E if application problem occurs.
     */
    T getInitializedById(int id) throws E;

    /**
     * Returns query object for the service.
     * 
     * @return query object for the service
     */
    GenericQuery<T, E> query();

    /**
    * This interface provides convenient query layer.
    * It encapsulates work with DAO restrictions layer.
    *
    * @author alexander-ilkun
    * @param <T> - class of the entity
    * @param <E> - application depended exception
    */
    interface GenericQuery<T, E extends GenericException> {

        /**
        * Gets many entities stored in the list.
        * Uses restrictions provided by hasXXXX() methods.
        * 
        * @param firstResult - index of first row starting from 1
        * @param maxResults - number of rows to be fetched
        * @return list of entities with specified restrictions
        * @throws E if application problem occurs.
        */
        List<T> find(int firstResult, int maxResults) throws E;

        /**
        * Gets many initialized entities stored in the list.
        * Uses restrictions provided by hasXXXX() methods.
        * 
        * @param firstResult - index of first row starting from 1
        * @param maxResults - number of rows to be fetched
        * @return list of entities with specified restrictions
        * @throws E if application problem occurs.
        */
        List<T> findInitialized(int firstResult, int maxResults) throws E;
    }

}
