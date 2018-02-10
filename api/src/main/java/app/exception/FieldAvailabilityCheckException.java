package app.exception;

import app.service.DataType;

/**
 * Created by Bublik on 22-Dec-17.
 */
public class FieldAvailabilityCheckException extends RuntimeException {

    private DataType userField;

    public FieldAvailabilityCheckException(DataType userField, Exception cause) {
        super("Can't check field availability");
        this.userField = userField;
    }

    public DataType getUserField() {
        return userField;
    }
}
