package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * Created by Bublik on 09-Nov-17.
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
public class Main {
    public static final boolean debug = false;
    public static final boolean defaultDatabase = true;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}