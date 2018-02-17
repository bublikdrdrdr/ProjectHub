package app.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("service.properties")
public class PropertiesLoader {

    private static ServiceProperties serviceProperties;

    public static ServiceProperties getServiceProperties(){
        return serviceProperties;
    }

    public static String getProperty(String key){
        return getServiceProperties().getProperty(key);
    }

    public static <T> T getProperty(String key, T defaultValue) {
        String value = getProperty(key);
        if (value == null) return defaultValue;
        Class clazz = defaultValue.getClass();
        try {
            if (Boolean.class == clazz) return (T) (Boolean.valueOf(value));
            if (Byte.class == clazz) return (T) (Byte.valueOf(value));
            if (Short.class == clazz) return (T) (Short.valueOf(value));
            if (Integer.class == clazz) return (T) (Integer.valueOf(value));
            if (Long.class == clazz) return (T) (Long.valueOf(value));
            if (Float.class == clazz) return (T) (Float.valueOf(value));
            if (Double.class == clazz) return (T) (Double.valueOf(value));
            if (String.class == clazz) return (T) value;
        } catch (ClassCastException e){
            throw new IllegalArgumentException("Property value and defaultValue must have same type", e);
        }
        throw new IllegalArgumentException("Unknown class " + defaultValue.getClass().getName());
    }

    @Autowired
    public void setServiceProperties(Environment environment){
        serviceProperties = new ServiceProperties(environment);
    }

    public class ServiceProperties{

        private Environment environment;

        private ServiceProperties(Environment environment){
            this.environment = environment;
        }

        public String getProperty(String key){
            return environment.getProperty(key);
        }

    }
}
