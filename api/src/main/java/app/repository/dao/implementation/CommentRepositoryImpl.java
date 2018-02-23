package app.repository.dao.implementation;

import app.repository.dao.CommentRepository;
import app.repository.entity.ProjectComment;
import app.repository.entity.UserBlock;
import app.repository.etc.CommentSearchParams;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Repository
@Transactional
public class CommentRepositoryImpl extends SearchableRepository<CommentSearchParams, ProjectComment> implements CommentRepository {

    @Override
    public ProjectComment get(long id) {
        return getEntity(ProjectComment.class, id, entity -> {
            Hibernate.initialize(entity.getOwner());
            Hibernate.initialize(entity.getProject());
        });
    }

    @Override
    public long save(ProjectComment projectComment) {
        saveEntity(projectComment);
        return projectComment.getId();
    }

    @Override
    public void remove(ProjectComment projectComment) {
        removeEntity(projectComment);
    }

    @Override
    public List<ProjectComment> search(CommentSearchParams searchParams) {
        return searchEntities(searchParams, ProjectComment.class);
    }

    @Override
    public long count(CommentSearchParams searchParams) {
        return countEntities(searchParams, ProjectComment.class);
    }

    @Override
    protected <T> CriteriaQuery<T> getSearchQuery(CommentSearchParams searchParams, CriteriaBuilder criteriaBuilder, Class<T> resultClass) {
        CriteriaQuery<T> query = criteriaBuilder.createQuery(resultClass);
        Root<ProjectComment> comment = query.from(ProjectComment.class);
        List<Predicate> predicates = new LinkedList<>();
        if (searchParams.project==null) throw new NullPointerException("BookmarkSearchParams can't be null");
        if (searchParams.message!=null) {
            predicates.add(criteriaBuilder.like(comment.get("text"), preparePattern(searchParams.message, false)));
        }
        query.where(criteriaBuilder.and((Predicate[])predicates.toArray()));
        Order order;
        Expression orderExpression = comment.get("posted");
        if (searchParams.desc) order = criteriaBuilder.desc(orderExpression); else order = criteriaBuilder.asc(orderExpression);
        query.orderBy(order);
        return query;
    }
}
