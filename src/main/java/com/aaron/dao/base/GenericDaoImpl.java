package com.aaron.dao.base;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class GenericDaoImpl<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK> {
    protected Class<T> mEntityClass;
    protected Class<PK> mPKClass;

    public GenericDaoImpl() {
        Type type = getClass().getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            type = getClass().getSuperclass().getGenericSuperclass();
        }
        this.mEntityClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
        this.mPKClass = (Class<PK>) ((ParameterizedType) type).getActualTypeArguments()[1];
    }

    @Override
    public void save(T entity) {
        super.getHibernateTemplate().save(entity);
    }

    @Override
    public void saveOrUpdate(T entity){
        super.getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public void update(T entity) {
        super.getHibernateTemplate().update(entity);
    }

    @Override
    public void update(String sql, SqlParam... params) {
        createQuery(sql, params).executeUpdate();
    }

    @Override
    public void batchUpdate(String sql, List<SqlParam[]> paramsList) {
        for (int i = 0; i < paramsList.size(); i++) {
            createQuery(sql, paramsList.get(i)).executeUpdate();
        }
    }

    @Override
    public void delete(T entity) {
        super.getHibernateTemplate().delete(entity);
    }

    @Override
    public void delete(String sql, SqlParam... params) {
        createQuery(sql, params).executeUpdate();
    }

    @Override
    public void deleteAll(Collection<T> entitys) {
        super.getHibernateTemplate().deleteAll(entitys);
    }

    @Override
    public List<T> findAll() {
        return findByCriteria();
    }

    @Override
    public T findByPK(final PK pk) {
        return super.getHibernateTemplate().get(mEntityClass, pk);
    }

    @Override
    public List find(String sql, SqlParam... params) {
        return createQuery(sql, params).list();
    }

    @Override
    public List find(String sql, int start, int limit, SqlParam... params) {
        return createQuery(sql, params).setFirstResult(start).setMaxResults(limit).list();
    }

    @Override
    public Object findUnique(String sql, SqlParam... params) {
        return createQuery(sql, params).uniqueResult();
    }

    @Override
    public List<T> findByCriteria(Criterion... criterions) {
        return (List<T>) createCriteria(criterions).list();
    }

    @Override
    public List<T> findByProperty(String propertyName, Object value) {
        return (List<T>) createCriteria(Restrictions.eq(propertyName, value)).list();
    }

    @Override
    public T findUniqueByProperty(String propertyName, Object value) {
        return (T) createCriteria(Restrictions.eq(propertyName, value)).uniqueResult();
    }

    @Override
    public Long count(String sql, SqlParam... params) {
        return (Long) createQuery(sql, params).uniqueResult();
    }


    private Query createQuery(String sql, SqlParam... params) {
        Query queryObject = super.currentSession().createQuery(sql);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                queryObject.setParameter(params[i].paramName, params[i].paramValue);
            }
        }
        return queryObject;
    }

    private Criteria createCriteria(Criterion... criterions) {
        Criteria criteria = super.currentSession().createCriteria(mEntityClass);
        if (null != criterions) {
            for (Criterion c : criterions) {
                criteria.add(c);
            }
        }
        return criteria;
    }
}
