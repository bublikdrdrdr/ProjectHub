package app.exceptions;

/**
 * Created by Bublik on 09-Nov-17.
 */
public class SetValueException extends Exception {

    public SetValueException() {
    }

    public SetValueException(String message) {
        super(message);
    }

    public SetValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public SetValueException(Throwable cause) {
        super(cause);
    }
}
