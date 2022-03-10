package com.gumisie.persistence.hibernate;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public abstract class GenericDao<T, I extends Serializable> {

    protected final SessionFactory sessionFactory;

    public GenericDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(T object){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.persist(object);

        tx.commit();
    }

    public T getById(Class<T> clazz, I id){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        T entity = session.get(clazz, id);

        tx.commit();
        return entity;
    }


}
