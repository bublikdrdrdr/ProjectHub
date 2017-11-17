package app.local;

import app.exceptions.SetValueException;

/**
 * Created by Bublik on 09-Nov-17.
 */
public interface LocalSettings {

    enum ValueType{DB_DIALECT, DB_URL, DB_USER, DB_PASS, DB_DRIVER, ACCESS_PASS, LAST_LOGIN}

    String getValue(ValueType valueType);
    void setValue(ValueType valueType, String value) throws SetValueException;
}
