package com.epam.pharmacy.dao;

import java.io.Serializable;
import java.util.List;

import com.epam.pharmacy.exceptions.PersistException;

/**
 * Unified object to control the perception of objects
 * @param <T> the type of the persistence's object
 * @param <PK> the type of primary key
 */
public interface GenericDao<T extends Identified<PK>, PK extends Serializable> {

    /** Create new row in DB about object */
    public T insert(T object) throws PersistException;

    /** Return object with row where key or null */
    public T getByPK(PK key) throws PersistException;

    /** Save persistence of object in DB */
    public void update(T object) throws PersistException;

    /** Delete row about object from DB */
    public void delete(T object) throws PersistException;

    /** Get list of objects which are rows in DB */
    public List<T> getAll() throws PersistException;
        
    /** Close all statements to the DB */
    public void close() throws PersistException;
}
