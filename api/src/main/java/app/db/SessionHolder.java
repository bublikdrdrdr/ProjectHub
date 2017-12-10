package app.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by Bublik on 21-Nov-17.
 */
@Component
public class SessionHolder {

    @Autowired
    private SessionFactory sessionFactory;

    private Logger logger = Logger.getLogger(SessionHolder.class);

    private Session session;
    private Transaction transaction;

    @Bean
    public Session getSession() throws HibernateException{
        if (sessionFactory==null) throw new HibernateException("SessionFactory is null");
        if (session==null || !session.isOpen()){
            session = sessionFactory.openSession();
        }
        return session;
    }

    public void closeSession(){
        try {
            if (session!=null) session.close();
        } catch (HibernateException he){
            logger.log(Logger.Level.WARN, he);
        }
    }
}
