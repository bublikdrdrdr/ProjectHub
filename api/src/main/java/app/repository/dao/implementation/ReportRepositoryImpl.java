package app.repository.dao.implementation;

import app.repository.dao.ReportRepository;
import app.repository.entity.Report;
import app.repository.entity.User;
import app.repository.etc.ReportSearchParams;
import app.repository.etc.UserSearchParams;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Repository
@Transactional
public class ReportRepositoryImpl extends SearchableRepository<ReportSearchParams, Report> implements ReportRepository {
    @Override
    public Report get(long id) {
        return getEntity(Report.class, id, entity -> {
            Hibernate.initialize(entity.getAdmin());
            Hibernate.initialize(entity.getSender());
        });
    }

    @Override
    public long save(Report report) {
        saveEntity(report);
        return report.getId();
    }

    @Override
    public void remove(Report report) {
        removeEntity(report);
    }

    @Override
    public List<Report> search(ReportSearchParams searchParams) {
        return searchEntities(searchParams, Report.class);
    }

    @Override
    public long count(ReportSearchParams searchParams) {
        return countEntities(searchParams, Report.class);
    }

    @Override
    protected <T> CriteriaQuery<T> getSearchQuery(ReportSearchParams searchParams, CriteriaBuilder criteriaBuilder, Class<T> resultClass) {
        CriteriaQuery<T> query = criteriaBuilder.createQuery(resultClass);
        Root<Report> report = query.from(Report.class);
        report.fetch("admin");
        report.fetch("sender");
        List<Predicate> predicates = new LinkedList<>();
        if (searchParams.admin!=null) predicates.add(criteriaBuilder.equal(report.get("admin"), searchParams.admin));
        if (searchParams.sender!=null) predicates.add(criteriaBuilder.equal(report.get("sender"), searchParams.sender));
        if (searchParams.message!=null) predicates.add(criteriaBuilder.like(report.get("message"), preparePattern(searchParams.message, false)));
        if (searchParams.reportType!=null) predicates.add(criteriaBuilder.equal(report.get("type"), searchParams.reportType));
        if (searchParams.sentAfter!=null) predicates.add(criteriaBuilder.greaterThanOrEqualTo(report.get("sent"), searchParams.sentAfter));
        if (searchParams.sentBefore!=null) predicates.add(criteriaBuilder.lessThanOrEqualTo(report.get("sent"), searchParams.sentBefore));
        if (searchParams.activeOnly) predicates.add(criteriaBuilder.isFalse(report.get("closed")));
        query.where(criteriaBuilder.and((Predicate[])predicates.toArray()));
        if (searchParams.getSort()!= ReportSearchParams.Sort.NONE) {
            Order order;
            Expression expression;
            switch (searchParams.getSort()) {
                case SENT: expression = report.get("sent");
                    break;
                default: throw new IllegalArgumentException("Unknown SortBy value: "+searchParams.getSort());
            }
            if (searchParams.desc) order = criteriaBuilder.desc(expression); else order = criteriaBuilder.asc(expression);
            query.orderBy(order);
        }
        return query;
    }
}
