package app.entities.dao;

import app.db.SessionHolder;
import app.entities.db.User;
import app.entities.rest.SearchParams;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

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
            return user;
        } finally {
            sessionHolder.closeSession();
        }
    }

    private boolean checkFlag(int value, int flag){
        return ((value&flag)==flag);
    }

    public User getUserByEmail(String email){
        try{
            Session session = sessionHolder.getSession();
            Query query = session.createQuery("FROM User WHERE email LIKE :email");
            query.setParameter("email", email);
            return (User)query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            sessionHolder.closeSession();
        }
    }

    public User getUserByUsername(String username){
        try{
            Session session = sessionHolder.getSession();
            Query query = session.createQuery("FROM User WHERE username LIKE :username");
            query.setParameter("username", username);
            return (User)query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            sessionHolder.closeSession();
        }
    }

    public long saveUser(User user){
        try {
            Session session = sessionHolder.getSession();
            sessionHolder.beginTransaction();
            return (long) session.save(user);
        } catch (Exception e){
            sessionHolder.rollback();
            throw e;
        } finally {
            sessionHolder.commit();
            sessionHolder.closeSession();
        }
    }

    public List<User> searchUsers(SearchParams searchParams){
        try {
            Session session = sessionHolder.getSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
            getSearchQuery(searchParams, criteriaBuilder, query);
            query.select(query.from(User.class));
            return session.createQuery(query).setFirstResult(searchParams.first).setMaxResults(searchParams.count).getResultList();
        } finally {
            sessionHolder.closeSession();
        }
    }

    public long searchCount(SearchParams searchParams){
        try{
            Session session = sessionHolder.getSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
            getSearchQuery(searchParams, criteriaBuilder, query);
            query.select(criteriaBuilder.count(query.from(User.class)));
            return session.createQuery(query).getSingleResult();
        } finally {
            sessionHolder.closeSession();
        }
    }

    private CriteriaQuery<?> getSearchQuery(SearchParams searchParams, CriteriaBuilder criteriaBuilder, CriteriaQuery<?> query){
        Root<User> user = query.from(User.class);
        List<Predicate> predicates = new LinkedList<>();
        if (searchParams.email != null) predicates.add(criteriaBuilder.like(criteriaBuilder.upper(user.get("email")), preparePattern(searchParams.email, searchParams.exact)));
        if (searchParams.username != null) predicates.add(criteriaBuilder.like(criteriaBuilder.upper(user.get("username")), preparePattern(searchParams.username, searchParams.exact)));
        if (searchParams.name != null) {
            Expression<String> nameUpper = criteriaBuilder.upper(user.get("name"));
            Expression<String> surnameUpper = criteriaBuilder.upper(user.get("surname"));
            Expression<String> expression = criteriaBuilder.concat(nameUpper, " ");
            expression = criteriaBuilder.concat(expression, surnameUpper);
            if (searchParams.exact){
                predicates.add(criteriaBuilder.like(expression, searchParams.name));
            } else {
                expression = criteriaBuilder.concat(expression, " ");
                expression = criteriaBuilder.concat(expression, nameUpper);
                predicates.add(criteriaBuilder.like(expression, namePattern(searchParams.name)));
            }
        }
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        if (searchParams.sortBy!= SearchParams.SortBy.NONE) {
            Order order;
            Expression expression;
            switch (searchParams.sortBy) {
                case REGISTERED: expression = user.get("registered");
                    break;
                case ONLINE: expression = user.get("last_login");
                    break;
                case NAME: expression = user.get("name");
                    break;
                case SURNAME: expression = user.get("surname");
                    break;
                default: throw new IllegalArgumentException("Unknown SortBy value: "+searchParams.sortBy.name());
            }
            if (searchParams.desc) order = criteriaBuilder.desc(expression); else order = criteriaBuilder.asc(expression);
            query.orderBy(order);
        }
        return query;
    }

    private String preparePattern(String s, boolean exact){
        return (exact?"%":"")+s.toUpperCase()+(exact?"%":"");
    }

    private String namePattern(String name){
        name = name.toUpperCase().replace(' ', '%');
        return '%'+name+'%';
    }

    public void removeUser(User user){
        try{
            Session session = sessionHolder.getSession();
            sessionHolder.beginTransaction();
            session.remove(user);
            sessionHolder.commit();
        } catch (Exception e){
            sessionHolder.rollback();
            throw e;
        } finally {
            sessionHolder.closeSession();
        }
    }
}
