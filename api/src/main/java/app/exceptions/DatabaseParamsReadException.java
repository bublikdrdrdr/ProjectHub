package app.exceptions;

/**
 * Created by Bublik on 19-Nov-17.
 */
public class DatabaseParamsReadException extends RuntimeException {

    public DatabaseParamsReadException() {
    }

    public DatabaseParamsReadException(String message) {
        super(message);
    }

    public DatabaseParamsReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseParamsReadException(Throwable cause) {
        super(cause);
    }

    public DatabaseParamsReadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
