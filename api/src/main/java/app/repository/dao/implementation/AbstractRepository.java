package app.repository.dao.implementation;

import app.db.SessionWrapper;
import app.repository.entity.Project;
import app.repository.entity.UserBlock;
import app.repository.etc.SearchParams;
import com.sun.istack.internal.Nullable;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.constraints.Null;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractRepository {

    @Autowired
    SessionWrapper wrapper;

    protected <E> E getEntity(Class<E> entityType, long id, @Nullable FieldsInitializer<E> initializer){
        try{
            E entity = wrapper.getSession().get(entityType,  id);
            if (entity==null) return null;
            if (initializer!=null) initializer.init(entity);
            return entity;
        } finally {
            wrapper.closeSession();
        }
    }

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

    protected String preparePattern(String s, boolean exact){
        return (exact?"%":"")+s.toUpperCase()+(exact?"%":"");
    }
}
