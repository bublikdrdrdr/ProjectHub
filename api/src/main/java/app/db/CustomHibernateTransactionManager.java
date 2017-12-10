package app.db;

import org.springframework.orm.hibernate5.HibernateTransactionManager;

/**
 * Created by Bublik on 09-Dec-17.
 */
public class CustomHibernateTransactionManager extends HibernateTransactionManager {
    @Override
    public void afterPropertiesSet() {
        try {
            super.afterPropertiesSet();
        } catch (Exception ignored){
            ignored.printStackTrace();
        }
    }
}
