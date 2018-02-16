package app.repository.dao.implementation;

import app.db.SessionWrapper;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

public abstract class AbstractRepository {

    @Autowired
    SessionWrapper wrapper;

    public long save(Object object){
        try {
            wrapper.beginTransaction();
            wrapper.getSession().saveOrUpdate(object);
            wrapper.commit();
            return 0;
        } catch (HibernateException he){
            wrapper.rollback();
            throw he;
        } finally {
            wrapper.closeSession();
        }
    }

    public void remove(Object object){
        try{
            wrapper.beginTransaction();
            wrapper.getSession().remove(object);
            wrapper.commit();
        } catch (Exception e){
            wrapper.rollback();
            throw e;
        } finally {
            wrapper.closeSession();
        }
    }
}
