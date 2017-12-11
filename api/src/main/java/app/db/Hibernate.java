package app.db;

import app.Properties;
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
    private DatabaseSettings databaseSettings;

    @Bean
    LocalSessionFactoryBean sessionFactory() {
        CustomLocalSessionFactoryBean sessionFactoryBean = new CustomLocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(getDataSource());
        sessionFactoryBean.setPackagesToScan(Properties.hibernate.packages);
        sessionFactoryBean.setHibernateProperties(databaseSettings.getHibernateProperties());
        return sessionFactoryBean;
    }

    @Bean
    private DataSource getDataSource(){
        return databaseSettings.getDataSource();
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager htm = new CustomHibernateTransactionManager();
        htm.setSessionFactory(sessionFactory);
        return htm;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}