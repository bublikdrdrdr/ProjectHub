package app.db;

import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.io.IOException;

/**
 * Created by Bublik on 09-Dec-17.
 */
public class CustomLocalSessionFactoryBean extends LocalSessionFactoryBean {
    @Override
    public void afterPropertiesSet() throws IOException {
        try {
            super.afterPropertiesSet();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
