package app.repository.dao.implementation;

import app.db.SessionWrapper;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepository {

    @Autowired
    SessionWrapper wrapper;

    protected void saveEntity(Object object){
        try {
            wrapper.beginTransaction();
            wrapper.getSession().saveOrUpdate(object);
            wrapper.commit();
        } catch (HibernateException he){
            wrapper.rollback();
            throw he;
        } finally {
            wrapper.closeSession();
        }
    }

    protected void removeEntity(Object object){
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
