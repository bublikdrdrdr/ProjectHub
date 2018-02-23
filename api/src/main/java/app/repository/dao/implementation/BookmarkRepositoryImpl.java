package app.repository.dao.implementation;

import app.repository.dao.BookmarkRepository;
import app.repository.entity.User;
import app.repository.entity.UserBookmark;
import app.repository.etc.BookmarkSearchParams;
import app.repository.etc.UserSearchParams;
import app.util.PropertiesLoader;
import org.hibernate.Hibernate;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Repository
@Transactional
public class BookmarkRepositoryImpl extends SearchableRepository<BookmarkSearchParams, UserBookmark> implements BookmarkRepository{

    @Override
    public UserBookmark get(long id) {
        return getEntity(UserBookmark.class, id, entity -> {
            Hibernate.initialize(entity.getUser());
            Hibernate.initialize(entity.getBookmarked());
        });
    }

    @Override
    public UserBookmark getByRelation(User user, User bookmarkedUser) {
        try{
            Query<UserBookmark> query = wrapper.getSession().createQuery("FROM UserBookmark ub WHERE ub.user=:user AND ub.bookmarked=:bookmarked", UserBookmark.class);
            query.setParameter("user", user);
            query.setParameter("bookmarked", bookmarkedUser);
            return query.uniqueResult();
        } finally {
            wrapper.closeSession();
        }
    }

    @Override
    public long save(UserBookmark userBookmark) {
        saveEntity(userBookmark);
        return userBookmark.getId();
    }

    @Override
    public void remove(UserBookmark userBookmark) {
        removeEntity(userBookmark);
    }

    @Override
    public List<UserBookmark> search(BookmarkSearchParams searchParams) {
        return searchEntities(searchParams, UserBookmark.class);
    }

    @Override
    public long count(BookmarkSearchParams searchParams) {
        return countEntities(searchParams, UserBookmark.class);
    }

    @Override
    protected <T> CriteriaQuery<T> getSearchQuery(BookmarkSearchParams searchParams, CriteriaBuilder criteriaBuilder, Class<T> resultClass) {
        CriteriaQuery<T> query = criteriaBuilder.createQuery(resultClass);
        Root<UserBookmark> bookmark = query.from(UserBookmark.class);
        List<Predicate> predicates = new LinkedList<>();
        if (searchParams.user==null) throw new NullPointerException("BookmarkSearchParams can't be null");
        predicates.add(criteriaBuilder.equal(bookmark.get("user"), searchParams.user));
        Join<UserBookmark, User> bookmarked = bookmark.join("bookmarked");
        if (searchParams.name!=null){
            Expression<String> nameUpper = criteriaBuilder.upper(bookmarked.get("name"));
            Expression<String> surnameUpper = criteriaBuilder.upper(bookmarked.get("surname"));
            Expression<String> expression = criteriaBuilder.concat(nameUpper, " ");
            expression = criteriaBuilder.concat(expression, surnameUpper);
            predicates.add(criteriaBuilder.like(expression, preparePattern(searchParams.name, false)));
        }
        if (searchParams.onlineOnly){
            Timestamp onlineTime = new Timestamp(System.currentTimeMillis()- PropertiesLoader.getProperty("user.online_timeout", 300000L));
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(bookmarked.get("lastOnline"), onlineTime));
        }
        query.where(criteriaBuilder.and((Predicate[])predicates.toArray()));
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        if (searchParams.getSort()!= BookmarkSearchParams.Sort.NONE) {
            Order order;
            Expression expression;
            switch (searchParams.getSort()){
                case REGISTERED: expression = bookmarked.get("registered");
                    break;
                case ONLINE: expression = bookmarked.get("lastOnline");
                    break;
                case NAME: expression = bookmarked.get("name");
                    break;
                case SURNAME: expression = bookmarked.get("surname");
                    break;
                case ADDED: expression = bookmark.get("added");
                    break;
                default: throw new IllegalArgumentException("Unknown SortBy value: "+searchParams.getSort());
            }
            if (searchParams.desc) order = criteriaBuilder.desc(expression); else order = criteriaBuilder.asc(expression);
            query.orderBy(order);
        }
        return query;
    }
}
