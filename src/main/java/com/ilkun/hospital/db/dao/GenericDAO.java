package com.ilkun.hospital.db.dao;

import com.ilkun.hospital.exception.GenericException;
import java.util.List;

/**
 * This interface provides DAO layer and basic CRUD operations.
 *
 * @author alexander-ilkun
 * @param <T> - class of the entity
 * @param <E> - application depended exception
 */
public interface GenericDAO<T, E extends GenericException> {

    
    /**
     * Saves entity.
     * 
     * @param entity - entity to save
     * @throws E if application exception occurs.
     */
    int save(T entity) throws E;

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
     * Gets many entities from stored in the list.
     * 
     * @param firstResult - index of first row starting from 1
     * @param maxResults - number of rows to be fetched
     * @return list of entities with specified restrictions
     * @throws E if database problem occurs.
     */
    List<T> getMany(int firstResult, int maxResults) throws E;

    /**
     * Gets many initialized entities stored in the list.
     * 
     * @param firstResult - index of first row starting from 1
     * @param maxResults - number of rows to be fetched
     * @return list of entities with specified restrictions
     * @throws E if database problem occurs.
     */
    List<T> getInitializedMany(int firstResult, int maxResults) throws E;

}
