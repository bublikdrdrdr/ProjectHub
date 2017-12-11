package app.entities.dao;

import app.entities.rest.DatabaseParams;
import app.exceptions.DatabaseParamsReadException;
import app.exceptions.SetValueException;
import app.local.LocalSettings;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Bublik on 19-Nov-17.
 */
@Repository
public class DatabaseSettingsImpl implements DatabaseSettings{

    private static final String defaultDriver = "com.mysql.jdbc.Driver";
    private static final String defaultUrl = "jdbc:mysql://localhost:3306/projecthub";
    private static final String defaultUser = "root";
    private static final String defaultPassword = "";
    private static final String defaultDialect = "org.hibernate.dialect.MariaDB53Dialect";
    private static final String defaultHbm2Ddl = app.Properties.hibernate.dropDatabaseOnStart?"create-drop":"update";
    private static final String defaultGQI = "true";

    @Autowired
    private LocalSettings localSettings;

    @Override
    public DatabaseParams getDatabaseParams() throws DatabaseParamsReadException{
        try {
            if (app.Properties.hibernate.defaultDatabase) return getDefaultDatabaseParams();
            return new DatabaseParams(localSettings.getValue(LocalSettings.ValueType.DB_DIALECT),
                    localSettings.getValue(LocalSettings.ValueType.DB_URL),
                    localSettings.getValue(LocalSettings.ValueType.DB_USER),
                    localSettings.getValue(LocalSettings.ValueType.DB_PASS),
                    localSettings.getValue(LocalSettings.ValueType.DB_DRIVER),
                    localSettings.getValue(LocalSettings.ValueType.DB_HBM2DDL_AUTO),
                    localSettings.getValue(LocalSettings.ValueType.DB_GQI));
        } catch (Exception e){
            throw new DatabaseParamsReadException("Can't read database params", e);
        }
    }

    @Override
    public void setDatabaseParams(DatabaseParams databaseParams) throws SetValueException {
        if (app.Properties.hibernate.defaultDatabase){
            Logger logger = Logger.getLogger(this.getClass());
            logger.log(Logger.Level.DEBUG, "Trying to overwrite default settings");
        } else {
            localSettings.setValue(LocalSettings.ValueType.DB_DIALECT, databaseParams.dialect);
            localSettings.setValue(LocalSettings.ValueType.DB_URL, databaseParams.url);
            localSettings.setValue(LocalSettings.ValueType.DB_USER, databaseParams.user);
            localSettings.setValue(LocalSettings.ValueType.DB_PASS, databaseParams.password);
            localSettings.setValue(LocalSettings.ValueType.DB_DRIVER, databaseParams.driver);
            localSettings.setValue(LocalSettings.ValueType.DB_HBM2DDL_AUTO, databaseParams.hbm2ddlAuto);
            localSettings.setValue(LocalSettings.ValueType.DB_GQI, databaseParams.globallyQuotedIdentifiers);
        }
    }

    @Override
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        DatabaseParams databaseParams = getDatabaseParams();
        dataSource.setDriverClassName(databaseParams.driver);
        dataSource.setUrl(databaseParams.url);
        dataSource.setUsername(databaseParams.user);
        dataSource.setPassword(databaseParams.password);
        return dataSource;
    }

    @Override
    public Properties getHibernateProperties() {
        if (app.Properties.hibernate.defaultDatabase) return getDefaultHibernateProperties();
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", localSettings.getValue(LocalSettings.ValueType.DB_HBM2DDL_AUTO));
        properties.setProperty("hibernate.dialect", localSettings.getValue(LocalSettings.ValueType.DB_DIALECT));
        properties.setProperty("hibernate.globally_quoted_identifiers",localSettings.getValue(LocalSettings.ValueType.DB_GQI));
        properties.setProperty("hibernate.show_sql", Boolean.toString(app.Properties.hibernate.debug));
        return properties;
    }

    private Properties getDefaultHibernateProperties(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", defaultHbm2Ddl);
        properties.setProperty("hibernate.dialect", defaultDialect);
        properties.setProperty("hibernate.globally_quoted_identifiers",defaultGQI);
        properties.setProperty("hibernate.show_sql", Boolean.toString(app.Properties.hibernate.debug));
        return properties;
    }

    private DatabaseParams getDefaultDatabaseParams(){
        return new DatabaseParams(defaultDialect, defaultUrl, defaultUser, defaultPassword, defaultDriver, defaultHbm2Ddl, defaultGQI);
    }
}
