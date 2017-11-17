package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Bublik on 09-Nov-17.
 */
@SpringBootApplication
public class Main {
    public static final boolean debug = false;
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}