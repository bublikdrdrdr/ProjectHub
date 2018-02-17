package app.repository.dao.implementation;

import app.db.SessionWrapper;
import app.repository.dao.UserRepository;
import app.repository.entity.User;
import app.repository.entity.UserBlock;
import app.repository.etc.SearchParams;
import app.repository.etc.UserSearchParams;
import javafx.util.converter.TimeStringConverter;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Bublik on 19-Nov-17.
 */
@Repository
@Transactional
public class UserRepositoryImpl extends AbstractRepository implements UserRepository{

    @Override
    public User get(long id){
        return get(id, EnumSet.noneOf(UserRepository.Include.class));
    }

    @Override
    public User get(long id, EnumSet<Include> includes){
        try {
            Session session = wrapper.getSession();
            User user = session.get(User.class, id);
            if (includes.contains(Include.IMAGES)) Hibernate.initialize(user.getImage());
            if (includes.contains(Include.PROJECTS)) Hibernate.initialize(user.getProjects());
            if (includes.contains(Include.LIKES)) Hibernate.initialize(user.getLikedProjects());
            if (includes.contains(Include.COMMENTS)) Hibernate.initialize(user.getComments());
            if (includes.contains(Include.BLOCKS)) Hibernate.initialize(user.getBlocks());
            if (includes.contains(Include.BOOKMARKS)) Hibernate.initialize(user.getBookmarks());
            if (includes.contains(Include.REPORTS)) Hibernate.initialize(user.getReports());
            if (includes.contains(Include.SENT_MESSAGES)) Hibernate.initialize(user.getSentMessages());
            if (includes.contains(Include.RECEIVED_MESSAGES)) Hibernate.initialize(user.getReceivedMessages());
            return user;
        } finally {
            wrapper.closeSession();
        }
    }

    @Override
    public User getByEmail(String email){
        try{
            Session session = wrapper.getSession();
            Query query = session.createQuery("FROM User WHERE email LIKE :email");
            query.setParameter("email", email);
            return (User)query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            wrapper.closeSession();
        }
    }

    @Override
    public User getByUsername(String username){
        try{
            Session session = wrapper.getSession();
            Query query = session.createQuery("FROM User WHERE username LIKE :username");
            query.setParameter("username", username);
            return (User)query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            wrapper.closeSession();
        }
    }

    @Override
    public long save(User user){
        super.saveEntity(user);
        return user.getId();
    }

    @Override
    public List<User> search(UserSearchParams searchParams){
        try {
            CriteriaBuilder criteriaBuilder = wrapper.getSession().getCriteriaBuilder();
            CriteriaQuery<User> query = getSearchQuery(searchParams, criteriaBuilder, User.class);
            query.select(query.from(User.class));
            return wrapper.getSession().createQuery(query).setFirstResult(searchParams.first).setMaxResults(searchParams.count).getResultList();
        } finally {
            wrapper.closeSession();
        }
    }

    @Override
    public long count(UserSearchParams searchParams){
        try{
            CriteriaBuilder criteriaBuilder = wrapper.getSession().getCriteriaBuilder();
            CriteriaQuery<Long> query = getSearchQuery(searchParams, criteriaBuilder, Long.class);
            query.select(criteriaBuilder.count(query.from(User.class)));
            return wrapper.getSession().createQuery(query).getSingleResult();
        } finally {
            wrapper.closeSession();
        }
    }

    private <T> CriteriaQuery<T> getSearchQuery(UserSearchParams searchParams, CriteriaBuilder criteriaBuilder, Class<T> resultClass){
        CriteriaQuery<T> query = criteriaBuilder.createQuery(resultClass);
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
        if (searchParams.getSort()!= UserSearchParams.Sort.NONE) {
            Order order;
            Expression expression;
            switch (searchParams.getSort()) {
                case REGISTERED: expression = user.get("registered");
                    break;
                case ONLINE: expression = user.get("last_login");
                    break;
                case NAME: expression = user.get("name");
                    break;
                case SURNAME: expression = user.get("surname");
                    break;
                default: throw new IllegalArgumentException("Unknown SortBy value: "+searchParams.getSort());
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

    @Override
    public void remove(User user){
        super.removeEntity(user);
    }
}
