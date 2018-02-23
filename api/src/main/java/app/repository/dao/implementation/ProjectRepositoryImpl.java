package app.repository.dao.implementation;

import app.db.SessionWrapper;
import app.repository.dao.ProjectRepository;
import app.repository.entity.AttachmentType;
import app.repository.entity.Project;
import app.repository.entity.ProjectAttachment;
import app.repository.etc.ProjectSearchParams;
import app.repository.etc.SearchParams;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Bublik on 21-Nov-17.
 */
@Repository
@Transactional
public class ProjectRepositoryImpl extends SearchableRepository<ProjectSearchParams, Project> implements ProjectRepository{

    @Override
    public Project get(long id) {
        return get(id, false);
    }

    public Project get(long id, boolean includeAttachments){
        return getEntity(Project.class, id, entity -> {
            if (includeAttachments) Hibernate.initialize(entity.getAttachments());
            Hibernate.initialize(entity.getAuthor());
        });
    }

    @Override
    public ProjectAttachment getAttachment(long id) {
        try{
            ProjectAttachment attachment = wrapper.getSession().get(ProjectAttachment.class, id);
            Hibernate.initialize(attachment.getBlobValue());
            return attachment;
        } finally {
            wrapper.closeSession();
        }
    }

    @Override
    public List<AttachmentType> getAttachmentTypes() {
        try {
            Query<AttachmentType> query = wrapper.getSession().createQuery("FROM AttachmentType", AttachmentType.class);
            return query.getResultList();
        } finally {
            wrapper.closeSession();
        }
    }

    @Override
    public long save(Project project) {
        super.saveEntity(project);
        return project.getId();
    }

    @Override
    public List<Project> search(ProjectSearchParams searchParams) {
        return searchEntities(searchParams, Project.class);
    }

    @Override
    public long count(ProjectSearchParams searchParams) {
        return countEntities(searchParams, Project.class);
    }

    @Override
    public void remove(Project project) {
        super.removeEntity(project);
    }

    @Override
    protected  <T> CriteriaQuery<T> getSearchQuery(ProjectSearchParams searchParams, CriteriaBuilder criteriaBuilder, Class<T> resultClass){
        CriteriaQuery<T> query = criteriaBuilder.createQuery(resultClass);
        Root<Project> project = query.from(Project.class);
        List<Predicate> predicates = new LinkedList<>();
        if (searchParams.authorId != null) predicates.add(criteriaBuilder.like(criteriaBuilder.upper(project.get("author")), searchParams.authorId.toString()));
        if (searchParams.exact) {
            if (searchParams.subject != null)
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(project.get("subject")), preparePattern(searchParams.subject, false)));
            if (searchParams.content != null)
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(project.get("content")), preparePattern(searchParams.content, false)));
        } else {
            List<Predicate> searchContentPredicates = new LinkedList<>();
            Expression<String> subjectExpression = criteriaBuilder.upper(project.get("subject"));
            Expression<String> contentExpression = criteriaBuilder.upper(project.get("content"));
            String subjectPattern = preparePattern(searchParams.subject, false);
            String contentPattern = preparePattern(searchParams.content, false);
            searchContentPredicates.add(criteriaBuilder.like(subjectExpression, subjectPattern));
            searchContentPredicates.add(criteriaBuilder.like(subjectExpression, contentPattern));
            searchContentPredicates.add(criteriaBuilder.like(contentExpression, subjectPattern));
            searchContentPredicates.add(criteriaBuilder.like(contentExpression, contentPattern));
            predicates.add(criteriaBuilder.or((Predicate[])searchContentPredicates.toArray()));
        }
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        if (searchParams.getSort()!= ProjectSearchParams.Sort.NONE) {
            Order order;
            Expression expression;
            switch (searchParams.getSort()) {
                case CREATED: expression = project.get("created");
                    break;
                case POSTED: expression = project.get("posted");
                    break;
                case SUBJECT: expression = project.get("subject");
                    break;
                case LIKES: expression = criteriaBuilder.count(project.get("surname"));
                    break;
                default: throw new IllegalArgumentException("Unknown SortBy value: "+searchParams.getSort());
            }
            if (searchParams.desc) order = criteriaBuilder.desc(expression); else order = criteriaBuilder.asc(expression);
            query.orderBy(order);
        }
        return query;
    }
}
