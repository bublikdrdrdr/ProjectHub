package app.exceptions;

import app.services.DataType;

/**
 * Created by Bublik on 22-Dec-17.
 */
public class FieldAvailabilityCheckException extends RuntimeException {

    public DataType userField;

    public FieldAvailabilityCheckException() {
    }

    public FieldAvailabilityCheckException(String message, DataType userField) {
        super(message);
        this.userField = userField;
    }
}
