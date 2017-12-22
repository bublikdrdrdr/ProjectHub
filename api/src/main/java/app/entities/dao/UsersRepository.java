package app.entities.dao;

import app.db.SessionHolder;
import app.entities.db.User;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
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
    public static final int INCLUDE_COMMENTS_FLAG = 0x00000008;
    public static final int INCLUDE_BLOCKS_FLAG = 0x00000010;
    public static final int INCLUDE_BOOKMARKS_FLAG = 0x00000020;
    public static final int INCLUDE_REPORTS_FLAG = 0x00000040;
    public static final int INCLUDE_SENT_MESSAGES_FLAG = 0x00000080;
    public static final int INCLUDE_RECEIVED_MESSAGES_FLAG = 0x00000100;

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
            if (checkFlag(flags, INCLUDE_COMMENTS_FLAG)) Hibernate.initialize(user.getComments());
            if (checkFlag(flags, INCLUDE_BLOCKS_FLAG)) Hibernate.initialize(user.getBlocks());
            if (checkFlag(flags, INCLUDE_BOOKMARKS_FLAG)) Hibernate.initialize(user.getBookmarks());
            if (checkFlag(flags, INCLUDE_REPORTS_FLAG)) Hibernate.initialize(user.getReports());
            if (checkFlag(flags, INCLUDE_SENT_MESSAGES_FLAG)) Hibernate.initialize(user.getSentMessages());
            if (checkFlag(flags, INCLUDE_RECEIVED_MESSAGES_FLAG)) Hibernate.initialize(user.getReceivedMessages());
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
        try{
            Session session = sessionHolder.getSession();
            Query query = session.createQuery("FROM Users WHERE email LIKE :email");
            query.setParameter("email", email);
            return (User)query.getSingleResult();
        } catch (NoResultException e) {
            sessionHolder.closeSession();
            return null;
        } catch (Exception e){
            sessionHolder.closeSession();
            throw e;
        } finally {
            System.out.println("FINALLY WORKS");
        }
    }
}
