package app.entities.dao;

import app.db.SessionHolder;
import app.entities.db.User;
import org.hibernate.Hibernate;
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

    public static final int INCLUDE_IMAGE_FLAG = 0x00000001;
    public static final int INCLUDE_PROJECTS_FLAG = 0x00000002;
    public static final int INCLUDE_LIKES_FLAG = 0x00000004;

    public User getUser(long id){
        return getUser(id, 0);
    }

    public User getUser(long id, int flags){
        try {
            Session session = sessionHolder.getSession();
            User user = session.get(User.class, id);
            if (checkFlag(flags, INCLUDE_IMAGE_FLAG)) Hibernate.initialize(user.getImage());
            if (checkFlag(flags, INCLUDE_PROJECTS_FLAG)) Hibernate.initialize(user.getProjects());
            if (checkFlag(flags, INCLUDE_LIKES_FLAG)) Hibernate.initialize(user.getLikedProjects());
            session.close();
            return user;
        } catch (HibernateException e){
            sessionHolder.closeSession();
            throw e;
        }
    }

    private boolean checkFlag(int value, int flag){
        return ((value&flag)==flag);
    }

    public User getUser(String email){
        return null;
    }
}
