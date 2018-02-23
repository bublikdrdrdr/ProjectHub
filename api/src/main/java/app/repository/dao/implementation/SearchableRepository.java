package app.repository.dao.implementation;

import app.repository.entity.Project;
import app.repository.etc.ProjectSearchParams;
import app.repository.etc.SearchParams;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public abstract class SearchableRepository<S extends SearchParams, E> extends AbstractRepository{

    protected List<E> searchEntities(S searchParams, Class<E> entityClass){
        try {
            CriteriaBuilder criteriaBuilder = wrapper.getSession().getCriteriaBuilder();
            CriteriaQuery<E> query = getSearchQuery(searchParams, criteriaBuilder, entityClass);
            query.select(query.from(entityClass));
            return wrapper.getSession().createQuery(query).setFirstResult(searchParams.first).setMaxResults(searchParams.count).getResultList();
        } finally {
            wrapper.closeSession();
        }
    }

    protected long countEntities(S searchParams, Class<E> entityClass) {
        try{
            CriteriaBuilder criteriaBuilder = wrapper.getSession().getCriteriaBuilder();
            CriteriaQuery<Long> query = getSearchQuery(searchParams, criteriaBuilder, Long.class);
            query.select(criteriaBuilder.count(query.from(entityClass)));
            return wrapper.getSession().createQuery(query).getSingleResult();
        } finally {
            wrapper.closeSession();
        }
    }

    protected abstract <T> CriteriaQuery<T> getSearchQuery(S searchParams, CriteriaBuilder criteriaBuilder, Class<T> resultClass);

}
