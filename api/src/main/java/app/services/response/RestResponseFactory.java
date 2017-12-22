package app.services.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.Entity;
import java.util.Map;

/**
 * Created by Bublik on 22-Dec-17.
 */
public class RestResponseFactory extends ResponseEntityFactory {

    @Override
    ResponseEntity<Object> getResponse(Entity entity) {
        return ResponseEntity.ok(entity);
    }

    @Override
    ResponseEntity<Object> getResponse(Entity entity, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(entity);
    }

    @Override
    ResponseEntity<Object> getResponse(ExceptionResponseMapping exceptionResponseMapping, Exception e) {
        return getResponse(exceptionResponseMapping, e, null);
    }

    @Override
    ResponseEntity<Object> getResponse(ExceptionResponseMapping exceptionResponseMapping, Exception e, Map<String, Object> info) {
        return ResponseEntity.status(exceptionResponseMapping.get(e)).body(info);
    }
}
