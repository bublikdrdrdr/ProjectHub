package app.repository.dao.implementation;

import app.repository.dao.LikeRepository;
import app.repository.entity.LikedProject;
import app.repository.entity.ProjectComment;
import app.repository.etc.LikeSearchParams;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Repository
@Transactional
public class LikeRepositoryImpl extends SearchableRepository<LikeSearchParams, LikedProject> implements LikeRepository {
    @Override
    public LikedProject get(long id) {
        return getEntity(LikedProject.class, id, entity -> {
            Hibernate.initialize(entity.getOwner());
            Hibernate.initialize(entity.getProject());
        });
    }

    @Override
    public long save(LikedProject likedProject) {
        saveEntity(likedProject);
        return likedProject.getId();
    }

    @Override
    public void remove(LikedProject likedProject) {
        removeEntity(likedProject);
    }

    @Override
    public List<LikedProject> search(LikeSearchParams searchParams) {
        return searchEntities(searchParams, LikedProject.class);
    }

    @Override
    public long count(LikeSearchParams searchParams) {
        return countEntities(searchParams, LikedProject.class);
    }

    @Override
    protected <T> CriteriaQuery<T> getSearchQuery(LikeSearchParams searchParams, CriteriaBuilder criteriaBuilder, Class<T> resultClass) {
        CriteriaQuery<T> query = criteriaBuilder.createQuery(resultClass);
        Root<LikedProject> like = query.from(LikedProject.class);
        List<Predicate> predicates = new LinkedList<>();
        if (searchParams.project==null && searchParams.user==null) throw new NullPointerException("Project and user are null");
        if (searchParams.project!=null) predicates.add(criteriaBuilder.equal(like.get("project"), searchParams.project));
        if (searchParams.user!=null) predicates.add(criteriaBuilder.equal(like.get("user"), searchParams.user));
        if (searchParams.markType!=null) predicates.add(criteriaBuilder.equal(like.get("markType"), searchParams.markType));
        query.where(criteriaBuilder.and((Predicate[])predicates.toArray()));
        Order order;
        Expression orderExpression = like.get("time");
        if (searchParams.desc) order = criteriaBuilder.desc(orderExpression); else order = criteriaBuilder.asc(orderExpression);
        query.orderBy(order);
        return query;
    }
}
