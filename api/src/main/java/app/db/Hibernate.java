package app.db;

import app.entities.dao.DatabaseSettings;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by Bublik on 20-Nov-17.
 */
@Configuration
@EnableTransactionManagement
//@ComponentScan({"app"})
public class Hibernate {

    @Autowired
    DatabaseSettings databaseSettings;

    @Bean
    LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(getDataSource());
        sessionFactoryBean.setPackagesToScan(new String[]{"app.entities.db"});
        sessionFactoryBean.setHibernateProperties(databaseSettings.getHibernateProperties());
        return sessionFactoryBean;
    }

    @Bean
    public DataSource getDataSource(){
        return databaseSettings.getDataSource();
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager htm = new HibernateTransactionManager();
        htm.setSessionFactory(sessionFactory);
        return htm;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}