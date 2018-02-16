package app.repository.dao.implementation;

import app.db.SessionWrapper;
import app.repository.dao.ProjectRepository;
import app.repository.entity.Project;
import app.repository.etc.ProjectSearchParams;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
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
public class ProjectRepositoryImpl extends AbstractRepository implements ProjectRepository{

    public Project get(long id){
        try{
            Session session = wrapper.getSession();
            Project project = session.get(Project.class, id);
            Hibernate.initialize(project.getAttachments());
            Hibernate.initialize(project.getAuthor());
            Hibernate.initialize(project.getAttachments());
            return project;
        } finally {
            wrapper.closeSession();
        }
    }

    @Override
    public long save(Project project) {
        super.save(project);
        return project.getId();
    }

    @Override
    public List<Project> search(ProjectSearchParams searchParams) {
        try {
            CriteriaBuilder criteriaBuilder = wrapper.getSession().getCriteriaBuilder();
            CriteriaQuery<Project> query = getSearchQuery(searchParams, criteriaBuilder, Project.class);
            query.select(query.from(Project.class));
            return wrapper.getSession().createQuery(query).setFirstResult(searchParams.first).setMaxResults(searchParams.count).getResultList();
        } finally {
            wrapper.closeSession();
        }
    }

    @Override
    public long count(ProjectSearchParams searchParams) {
        try{
            CriteriaBuilder criteriaBuilder = wrapper.getSession().getCriteriaBuilder();
            CriteriaQuery<Long> query = getSearchQuery(searchParams, criteriaBuilder, Long.class);
            query.select(criteriaBuilder.count(query.from(Project.class)));
            return wrapper.getSession().createQuery(query).getSingleResult();
        } finally {
            wrapper.closeSession();
        }
    }

    @Override
    public void remove(Project project) {
        super.remove(project);
    }

    private <T> CriteriaQuery<T> getSearchQuery(ProjectSearchParams searchParams, CriteriaBuilder criteriaBuilder, Class<T> resultClass){
        CriteriaQuery<T> query = criteriaBuilder.createQuery(resultClass);
        Root<Project> project = query.from(Project.class);
        List<Predicate> predicates = new LinkedList<>();
        if (searchParams.authorId != null) predicates.add(criteriaBuilder.like(criteriaBuilder.upper(project.get("author")), searchParams.authorId.toString()));
        if (searchParams.subject != null) predicates.add(criteriaBuilder.like(criteriaBuilder.upper(project.get("subject")), preparePattern(searchParams.subject, searchParams.exact)));
        if (searchParams.content != null) predicates.add(criteriaBuilder.like(criteriaBuilder.upper(project.get("content")), preparePattern(searchParams.content, false)));
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

    private String preparePattern(String s, boolean exact){
        return (exact?"%":"")+s.toUpperCase()+(exact?"%":"");
    }

}
