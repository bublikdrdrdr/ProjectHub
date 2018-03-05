package app.repository.dao.implementation;

import app.repository.dao.BlockRepository;
import app.repository.entity.User;
import app.repository.entity.UserBlock;
import app.repository.etc.BlockSearchParams;
import app.repository.etc.UserSearchParams;
import jdk.nashorn.internal.ir.Block;
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
public class BlockRepositoryImpl extends SearchableRepository<BlockSearchParams, UserBlock> implements BlockRepository {

    @Override
    public UserBlock get(long id) {
        return getEntity(UserBlock.class, id, entity -> {
            Hibernate.initialize(entity.getUser());
            Hibernate.initialize(entity.getAdmin());
            Hibernate.initialize(entity.getCanceledBy());
        });
    }

    @Override
    public long save(UserBlock block) {
        saveEntity(block);
        return block.getId();
    }

    @Override
    public void remove(UserBlock block) {
        removeEntity(block);
    }

    @Override
    public List<UserBlock> search(BlockSearchParams searchParams) {
        return searchEntities(searchParams, UserBlock.class);
    }

    @Override
    public long count(BlockSearchParams searchParams) {
        return countEntities(searchParams, UserBlock.class);
    }

    protected  <T> CriteriaQuery<T> getSearchQuery(BlockSearchParams searchParams, CriteriaBuilder criteriaBuilder, Class<T> resultClass){
        CriteriaQuery<T> query = criteriaBuilder.createQuery(resultClass);
        Root<UserBlock> block = query.from(UserBlock.class);
        block.fetch("user");
        block.fetch("admin");
        block.fetch("canceledBy");
        List<Predicate> predicates = new LinkedList<>();
        if (searchParams.admin!=null) predicates.add(criteriaBuilder.equal(block.get("admin"), searchParams.admin));
        if (searchParams.user!=null) predicates.add(criteriaBuilder.equal(block.get("user"), searchParams.user));
        if (searchParams.activeOnly){
            List<Predicate> activeBlockPredicates = new LinkedList<>();
            activeBlockPredicates.add(criteriaBuilder.lessThanOrEqualTo(block.get("start"), criteriaBuilder.currentTimestamp()));
            activeBlockPredicates.add(criteriaBuilder.greaterThanOrEqualTo(block.get("end"), criteriaBuilder.currentTimestamp()));
            activeBlockPredicates.add(criteriaBuilder.isFalse(block.get("canceled")));
            Expression<Long> countExpression = criteriaBuilder.count(criteriaBuilder.and((Predicate[])activeBlockPredicates.toArray()));
            predicates.add(criteriaBuilder.ge(countExpression,1));
        }
        query.where(criteriaBuilder.and((Predicate[])predicates.toArray()));
        Order order;
        Expression orderExpression = block.get("id");
        if (searchParams.desc) order = criteriaBuilder.desc(orderExpression); else order = criteriaBuilder.asc(orderExpression);
        query.orderBy(order);
        return query;
    }

    @Override
    public boolean isBlocked(User user) {
        return count(new BlockSearchParams(null, 0, 2, null, user, true))>0;
    }
}
