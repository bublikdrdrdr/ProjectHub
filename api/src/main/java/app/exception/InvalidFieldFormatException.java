package app.exception;

import app.service.DataType;

import java.util.EnumSet;

public class InvalidFieldFormatException extends RuntimeException {

    private EnumSet<DataType> set;

    public InvalidFieldFormatException(EnumSet<DataType> set) {
        this.set = set;
    }

    public EnumSet<DataType> getSet() {
        return set;
    }
}
