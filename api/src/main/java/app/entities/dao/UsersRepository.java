package app.entities.dao;

import app.db.SessionHolder;
import app.entities.db.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Bublik on 19-Nov-17.
 */
@Repository
@Transactional
public class UsersRepository {

    @Autowired
    private SessionHolder sessionHolder;

    public User getUser(long id){
        try {
            Session session = sessionHolder.getSession();
            User user = session.get(User.class, id);
            session.close();
            return user;
        } catch (HibernateException e){
            sessionHolder.closeSession();
            throw e;
        }
    }

    public User getUser(String email){
        return null;
    }
}
