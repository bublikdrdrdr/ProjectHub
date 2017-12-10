package app.entities.dao;

import app.db.SessionHolder;
import app.entities.db.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Bublik on 19-Nov-17.
 */
@Repository
@Transactional
public class UsersRepository {

    @Autowired
    SessionHolder sessionHolder;

    public User getUser(long id){
        try {
            Session session = sessionHolder.getSession();
            Query query = session.createQuery("from User where id = :id ");
            query.setParameter("id", id);
            List list = query.list();
            session.close();
            if (list.size() > 0) {
                return (User) list.get(0);
            }
        } catch (HibernateException e){
            sessionHolder.closeSession();
            throw e;
        }
        return null;
    }

    public User getUser(String email){
        return null;
    }
}
