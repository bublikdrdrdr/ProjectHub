package app;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.FallbackWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

/**
 * Created by Bublik on 09-Nov-17.
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class, SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class,
        FallbackWebSecurityAutoConfiguration.class,
        OAuth2AutoConfiguration.class})
@Configuration
//@PropertySource("service.properties")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    /*@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }*/
}