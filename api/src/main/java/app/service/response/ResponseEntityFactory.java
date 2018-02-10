package app.service.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.Entity;
import java.util.Map;

/**
 * Created by Bublik on 18-Dec-17.
 */
public abstract class ResponseEntityFactory {

    abstract ResponseEntity<Object> getResponse(Entity entity);
    abstract ResponseEntity<Object> getResponse(Entity entity, HttpStatus httpStatus);
    abstract ResponseEntity<Object> getResponse(ExceptionResponseMapping exceptionResponseMapping, Exception e);
    abstract ResponseEntity<Object> getResponse(ExceptionResponseMapping exceptionResponseMapping, Exception e, Map<String, Object> info);
}
