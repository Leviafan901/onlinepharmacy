package com.epam.pharmacy.dao;

import java.util.List;
import java.util.Optional;

import com.epam.pharmacy.exceptions.DaoException;

/**
 * Unified object to control the perception of objects
 * @param <T> the type of the persistence's object
 * @param <Long> the type of primary key
 */
public interface GenericDao<T extends Identifiable> {

    /** Create new row in DB about object */
    public T insert(T object) throws DaoException;

    /** Return object with row where key or null */
    public Optional<T> getById(Long key) throws DaoException;

    /** Save persistence of object in DB */
    public boolean update(T object) throws DaoException;

    /** Get list of objects which are rows in DB */
    public List<T> getAll() throws DaoException;
}
