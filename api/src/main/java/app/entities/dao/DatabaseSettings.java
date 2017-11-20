package app.entities.dao;

import app.entities.rest.DatabaseParams;
import app.exceptions.DatabaseParamsReadException;
import app.exceptions.SetValueException;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Bublik on 19-Nov-17.
 */
public interface DatabaseSettings {

    DatabaseParams getDatabaseParams() throws DatabaseParamsReadException;
    void setDatabaseParams(DatabaseParams databaseParams) throws SetValueException;

    DataSource getDataSource();
    Properties getHibernateProperties();
}
