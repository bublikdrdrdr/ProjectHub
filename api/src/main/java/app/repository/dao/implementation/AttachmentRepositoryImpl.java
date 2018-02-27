package app.repository.dao.implementation;

import app.repository.dao.AttachmentRepository;
import app.repository.entity.AttachmentType;
import app.repository.entity.Project;
import app.repository.entity.ProjectAttachment;
import app.repository.etc.AttachmentSearchParams;
import app.repository.etc.ProjectSearchParams;
import org.hibernate.Hibernate;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Repository
@Transactional
public class AttachmentRepositoryImpl extends SearchableRepository<AttachmentSearchParams, ProjectAttachment> implements AttachmentRepository {


    @Override
    public ProjectAttachment get(long id, boolean includeValue, boolean includeProject) {
        return getEntity(ProjectAttachment.class, id, entity -> {
            if (includeValue) Hibernate.initialize(entity.getBlobValue());
            if (includeProject) Hibernate.initialize(entity.getProject());
        });
    }

    @Override
    public ProjectAttachment get(long id) {
        return get(id, true, false);
    }

    @Override
    public long save(ProjectAttachment attachment) {
        saveEntity(attachment);
        return attachment.getId();
    }

    @Override
    public void remove(ProjectAttachment attachment) {
        removeEntity(attachment);
    }

    @Override
    public List<ProjectAttachment> search(AttachmentSearchParams searchParams) {
        return searchEntities(searchParams, ProjectAttachment.class);
    }

    @Override
    public long count(AttachmentSearchParams searchParams) {
        return countEntities(searchParams, ProjectAttachment.class);
    }

    @Override
    protected <T> CriteriaQuery<T> getSearchQuery(AttachmentSearchParams searchParams, CriteriaBuilder criteriaBuilder, Class<T> resultClass) {
        CriteriaQuery<T> query = criteriaBuilder.createQuery(resultClass);
        Root<ProjectAttachment> attachment = query.from(ProjectAttachment.class);
        List<Predicate> predicates = new LinkedList<>();
        if (searchParams.project != null)
            predicates.add(criteriaBuilder.equal(attachment.get("project"), searchParams.project));
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        Order order;
        Expression expression = attachment.get("id");
        if (searchParams.desc) order = criteriaBuilder.desc(expression);
        else order = criteriaBuilder.asc(expression);
        query.orderBy(order);
        return query;
    }

    @Override
    public AttachmentType getAttachmentType(long id) {
        try{
            return wrapper.getSession().get(AttachmentType.class, id);
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
}
