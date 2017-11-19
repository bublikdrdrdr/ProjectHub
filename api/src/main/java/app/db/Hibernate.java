package app.db;

import app.local.LocalSettings;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

import static app.local.LocalSettings.ValueType.*;

/**
 * Created by Bublik on 09-Nov-17.
 */
public class Hibernate {

    private static Hibernate ourInstance = new Hibernate();

    public static Hibernate getInstance() {
        return ourInstance;
    }

    private Hibernate() {
        resetSession();
    }

    public void resetSession(){
        sessionFactory = getSessionFactory();
    }

    @Autowired
    LocalSettings localSettings;

    private SessionFactory sessionFactory;

    private SessionFactory getSessionFactory() {
        try {
            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

            Map<String, String> settings = new HashMap<>();
            settings.put(Environment.DRIVER, localSettings.getValue(DB_DRIVER));
            settings.put(Environment.URL, localSettings.getValue(DB_URL));
            settings.put(Environment.USER, localSettings.getValue(DB_USER));
            settings.put(Environment.PASS, localSettings.getValue(DB_PASS));
            settings.put(Environment.DIALECT, localSettings.getValue(DB_DIALECT));
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.HBM2DDL_AUTO, "create");
            settings.put(Environment.FORMAT_SQL, "true");
            registryBuilder.applySettings(settings);
            MetadataSources metadataSources = new MetadataSources(
                    registryBuilder.build());
            //entity classes mapping
            //metadataSources.addAnnotatedClass(User.class);


            SessionFactory sessionFactory = metadataSources
                    .getMetadataBuilder().build()
                    .getSessionFactoryBuilder().build();
            return sessionFactory;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Session getSession()
            throws HibernateException {
        if (sessionFactory==null) throw new HibernateException("SessionFactory is null");
        return sessionFactory.openSession();
    }

    public EntityManager getEntityManager(){
        return getSession().getEntityManagerFactory().createEntityManager();
    }
}
