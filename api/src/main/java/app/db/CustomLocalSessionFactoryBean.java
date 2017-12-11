package app.db;

import app.entities.db.AttachmentType;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.IOException;
import java.util.List;

/**
 * Created by Bublik on 09-Dec-17.
 */
public class CustomLocalSessionFactoryBean extends LocalSessionFactoryBean {


    @Override
    public void afterPropertiesSet() throws IOException {
        try {
            super.afterPropertiesSet();
            insertInitialData();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void insertInitialData() throws HibernateException {
        SessionFactory sessionFactory = getObject();
        if (sessionFactory == null) return;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<AttachmentType> list = AttachmentType.getValues();
            for (AttachmentType attachmentType: list){
                try {
                    Query query = session.createQuery("FROM AttachmentType WHERE name LIKE :typeName");
                    query.setParameter("typeName", attachmentType.getName());
                    query.getSingleResult();
                } catch (NoResultException e){
                    session.save(attachmentType);
                }
            }
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
            session.close();
            throw e;
        }
    }
}
