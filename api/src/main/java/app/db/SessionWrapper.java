package app.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SessionWrapper {

    private final
    SessionFactory sessionFactory;

    private Logger logger = Logger.getLogger(SessionWrapper.class);

    private Session session;
    private Transaction transaction;

    @Autowired
    public SessionWrapper(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Bean
    public Session getSession() throws HibernateException{
        if (sessionFactory==null) throw new HibernateException("SessionFactory is null");
        if (session==null || !session.isOpen()){
            session = openSession();
        }
        return session;
    }

    public Session openSession(){
        /*try{
            if (session!=null && session.isOpen()){
                session.close();
            }
        } catch (HibernateException ignored){}*/
        return sessionFactory.openSession();
    }

    public Transaction beginTransaction(){
        getSession();
        transaction = session.beginTransaction();
        return transaction;
    }

    public void commit(){
        transaction.commit();
    }

    public void rollback(){
        transaction.rollback();
    }

    public void closeSession(){
        try {
            transaction = null;
            if (session!=null) session.close();
        } catch (HibernateException he){
            logger.log(Logger.Level.WARN, he);
        }
    }
}
