package app.repository.dao.implementation;

import app.repository.dao.MessageRepository;
import app.repository.entity.Message;
import app.repository.entity.User;
import app.repository.etc.MessageSearchParams;
import app.repository.etc.UserSearchParams;
import org.hibernate.Hibernate;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Repository
@Transactional
public class MessageRepositoryImpl extends SearchableRepository<MessageSearchParams, Message> implements MessageRepository {
    @Override
    public Message get(long id) {
        return getEntity(Message.class, id, entity -> {
            Hibernate.initialize(entity.getImage());
            Hibernate.initialize(entity.getReceiver());
            Hibernate.initialize(entity.getSender());
        });
    }

    @Override
    public long save(Message message) {
        saveEntity(message);
        return message.getId();
    }

    @Override
    public void remove(Message message) {
        removeEntity(message);
    }

    @Override
    public List<Message> search(MessageSearchParams searchParams) {
        if (searchParams.interlocutor != null)
            return searchEntities(searchParams, Message.class);
        else return searchDialogs(searchParams);
    }

    @Override
    public long count(MessageSearchParams searchParams) {
        if (searchParams.interlocutor != null)
            return countEntities(searchParams, Message.class);
        else return countDialogs(searchParams);
    }

    @Override
    protected <T> CriteriaQuery<T> getSearchQuery(MessageSearchParams searchParams, CriteriaBuilder criteriaBuilder, Class<T> resultClass) {
        CriteriaQuery<T> query = criteriaBuilder.createQuery(resultClass);
        Root<Message> message = query.from(Message.class);
        List<Predicate> predicates = new LinkedList<>();
        if (searchParams.owner == null) throw new NullPointerException("Owner can't be null");
        Predicate ownerPredicate;
        if (searchParams.interlocutor == null) {
            //FIXME: create criteriaquery instead of hql query
            throw new NullPointerException("Interlocutor is null. This method can only find messages between 2 users");
        } else {
            Expression senderExpression = message.get("sender");
            Expression receiverExpression = message.get("receiver");
            List<Predicate> ownerIsSenderPredicates = new LinkedList<>();
            List<Predicate> ownerIdReceiverPredicates = new LinkedList<>();
            ownerIsSenderPredicates.add(criteriaBuilder.equal(senderExpression, searchParams.owner));
            ownerIsSenderPredicates.add(criteriaBuilder.equal(receiverExpression, searchParams.interlocutor));
            if (!searchParams.includeRemoved)
                ownerIsSenderPredicates.add(criteriaBuilder.isFalse(message.get("deletedBySender")));
            ownerIdReceiverPredicates.add(criteriaBuilder.equal(senderExpression, searchParams.interlocutor));
            ownerIdReceiverPredicates.add(criteriaBuilder.equal(receiverExpression, searchParams.owner));
            if (!searchParams.includeRemoved)
                ownerIdReceiverPredicates.add(criteriaBuilder.isFalse(message.get("deletedByReceiver")));
            ownerPredicate = criteriaBuilder.or(
                    criteriaBuilder.and((Predicate[]) ownerIsSenderPredicates.toArray()),
                    criteriaBuilder.and((Predicate[]) ownerIdReceiverPredicates.toArray()));
        }
        predicates.add(ownerPredicate);
        if (searchParams.unreadOnly) predicates.add(criteriaBuilder.isFalse(message.get("seen")));
        if (searchParams.message != null)
            predicates.add(criteriaBuilder.like(message.get("message"), preparePattern(searchParams.message, false)));
        query.where(criteriaBuilder.and((Predicate[])predicates.toArray()));
        Order order;
        Expression expression;
        expression = message.get("sent");
        if (searchParams.desc) order = criteriaBuilder.desc(expression);
        else order = criteriaBuilder.asc(expression);
        query.orderBy(order);
        return query;
    }

    private List<Message> searchDialogs(MessageSearchParams searchParams){
        try{
            Query<Message> query = wrapper.getSession().createQuery(getDialogsQuery(searchParams), Message.class);
            query.setParameter("owner", searchParams.owner);
            return query.setFirstResult(searchParams.first).setMaxResults(searchParams.count).getResultList();
        } finally {
            wrapper.closeSession();
        }
    }

    private long countDialogs(MessageSearchParams searchParams){
        try{
            Query<Long> query = wrapper.getSession().createQuery("SELECT COUNT(*) "+getDialogsQuery(searchParams), Long.class);
            query.setParameter("owner", searchParams.owner);
            return query.uniqueResult();
        } finally {
            wrapper.closeSession();
        }
    }

    private String getDialogsQuery(MessageSearchParams searchParams){
        return "FROM Message mm WHERE (mm.id IN " +
                "(SELECT MAX(m.id) as idd FROM Message m \n" +
                "JOIN User u ON (m.sender=u.id OR m.receiver=u.id) WHERE " +
                "(m.sender=:owner AND m.receiver=u.id"+ (searchParams.includeRemoved?"":" AND m.removedBySender = false")+") OR " +
                "(m.receiver=:owner AND m.sender=u.id"+(searchParams.includeRemoved?"":" AND m.removedByReceiver = false")+") OR " +
                "(m.receiver=:owner AND m.sender=:owner"+(searchParams.includeRemoved?"":" AND m.removedBySender = false")+") \n" +
                "GROUP BY u.id))"+(searchParams.unreadOnly?" AND m.seen=false":"")+(searchParams.message==null?"":" AND (m.message LIKE \"%"+searchParams.message+"%\")");
    }

    @Override
    public void setAllSeen(User owner, User interlocutor) {
        //TODO
    }
}
