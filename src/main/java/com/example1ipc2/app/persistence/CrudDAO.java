package com.example1ipc2.app.persistence;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author elvis
 */
public abstract class CrudDAO<T> {

    public abstract T insert(T entity) throws SQLException;
    public abstract T findById(Long id) throws SQLException;
    public abstract List<T> findAll() throws SQLException;
    public abstract void update(T entity) throws SQLException;
    public abstract void delete(Long id) throws SQLException;
}
