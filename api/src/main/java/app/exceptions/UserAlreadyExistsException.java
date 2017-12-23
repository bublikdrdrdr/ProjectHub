package app.exceptions;

import java.util.EnumSet;

/**
 * Created by Bublik on 22-Dec-17.
 */
public class UserAlreadyExistsException extends RuntimeException{

    enum ConflictField {EMAIL, USERNAME}

    public EnumSet<ConflictField> conflictFields;

    public UserAlreadyExistsException() {
    }

    public UserAlreadyExistsException(EnumSet<ConflictField> conflictFields) {
        this.conflictFields = conflictFields;
    }
}
