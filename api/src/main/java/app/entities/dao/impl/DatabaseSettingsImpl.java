package app.entities.dao.impl;

import app.Main;
import app.entities.dao.DatabaseSettings;
import app.entities.rest.DatabaseParams;
import app.exceptions.DatabaseParamsReadException;
import app.exceptions.SetValueException;
import app.local.LocalSettings;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Bublik on 19-Nov-17.
 */
@Repository
public class DatabaseSettingsImpl implements DatabaseSettings{

    private static final String defaultDriver = "com.mysql.jdbc.Driver";
    private static final String defaultUrl = "jdbc:mysql://localhost:3306/projecthub";
    private static final String defaultUser = "root";
    private static final String defaultPassword = "";
    private static final String defaultDialect = "org.hibernate.dialect.MySQLDialect";

    @Autowired
    LocalSettings localSettings;

    @Override
    public DatabaseParams getDatabaseParams() throws DatabaseParamsReadException{
        try {
            if (Main.defaultDatabase) return getDefaultDatabaseParams();
            return new DatabaseParams(localSettings.getValue(LocalSettings.ValueType.DB_DIALECT),
                    localSettings.getValue(LocalSettings.ValueType.DB_URL),
                    localSettings.getValue(LocalSettings.ValueType.DB_USER),
                    localSettings.getValue(LocalSettings.ValueType.DB_PASS),
                    localSettings.getValue(LocalSettings.ValueType.DB_DRIVER));
        } catch (Exception e){
            throw new DatabaseParamsReadException("Can't read database params", e);
        }
    }

    @Override
    public void setDatabaseParams(DatabaseParams databaseParams) throws SetValueException {
        if (Main.defaultDatabase){
            Logger logger = Logger.getLogger(this.getClass());
            logger.log(Logger.Level.DEBUG, "Trying to overwrite default settings");
        } else {
            localSettings.setValue(LocalSettings.ValueType.DB_DIALECT, databaseParams.dialect);
            localSettings.setValue(LocalSettings.ValueType.DB_URL, databaseParams.url);
            localSettings.setValue(LocalSettings.ValueType.DB_USER, databaseParams.user);
            localSettings.setValue(LocalSettings.ValueType.DB_PASS, databaseParams.password);
            localSettings.setValue(LocalSettings.ValueType.DB_DRIVER, databaseParams.driver);
        }
    }

    private DatabaseParams getDefaultDatabaseParams(){
        return new DatabaseParams(defaultDialect, defaultUrl, defaultUser, defaultPassword, defaultDriver);
    }
}
