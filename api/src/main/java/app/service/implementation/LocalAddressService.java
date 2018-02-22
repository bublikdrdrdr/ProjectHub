package app.service.implementation;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Bublik on 23-Dec-17.
 */
@Component
public class LocalAddressService {

    private Logger logger = Logger.getLogger(LocalAddressService.class);

    private final Environment environment;

    @Autowired
    public LocalAddressService(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void showLocalAddress(){
        String port = environment.getProperty("local.server.port");
        if (port==null) port = "maybe 8080 (default)";
        String ip = "unknown";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.debug("Can't get localhost ip", e);
        }
        String message = "Your server is up and running at IP: "+ip+", port: "+port;
        logger.log(Logger.Level.INFO, message);
    }
}
