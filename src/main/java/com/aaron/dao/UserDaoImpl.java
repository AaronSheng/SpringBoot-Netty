package com.aaron.dao;

import com.aaron.dao.base.GenericDaoImpl;
import com.aaron.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Repository
@Transactional("serverTransactionManager")
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {
    @Resource(name = "serverSessionFactory")
    public void setServerSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public Boolean has(Long id) {
        return findByPK(id) != null;
    }
}
