package app.exception;

public class InvalidTypeException extends RuntimeException {

    public InvalidTypeException(Object type) {
        super("Invalid type value: "+type.toString());
    }
}
