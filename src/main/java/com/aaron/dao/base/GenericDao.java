package com.aaron.dao.base;

import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface GenericDao <T, PK extends Serializable> {
    /* --------------< save >----------------------*/
    void save(T entity);

    void saveOrUpdate(T entity);


    /* -------------< update >-------------*/
    void update(T entity);

    void update(String sql, SqlParam... params);

    void batchUpdate(String sql, List<SqlParam[]> paramsList);

    /* --------------< delete >----------------------*/
    void delete(T entity);

    void delete(String sql, SqlParam... params);

    void deleteAll(Collection<T> entitys);

    /* --------------< select >----------------------*/
    List<T> findAll();

    T findByPK(final PK id);

    List find(String sql, SqlParam... params);

    List find(String sql, int start, int limit, SqlParam... params);

    Object findUnique(String sql, SqlParam... params);

    List<T> findByCriteria(Criterion... criterions);

    List<T> findByProperty(String propertyName, Object value);

    T findUniqueByProperty(String propertyName, Object value);

    /* --------------< count >------------------------*/
    Long count(String sql, SqlParam... params);
}
