package app.services.local;

import app.db.SQLite;
import app.exceptions.SetValueException;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

import static app.services.local.LocalDatabaseQueryHelper.DB_NAME;

/**
 * Created by Bublik on 09-Nov-17.
 */
@Service
public class LocalSettingsImpl implements LocalSettings{

    private SQLite sqLite;


    public LocalSettingsImpl() throws SQLException {
        try {
            sqLite = new SQLite(DB_NAME);
            createTable();
        } catch (ClassNotFoundException e) {
            throw new SQLException("Can't get access to local db", e);
        }
    }

    private void createTable() throws SQLException{
        sqLite.executeUpdate(LocalDatabaseQueryHelper.CREATE_TABLE_QUERY);
    }

    @Override
    public String getValue(ValueType valueType){
        try {
            ResultSet resultSet = sqLite.select(LocalDatabaseQueryHelper.selectValue(valueType.toString()));
            if (!resultSet.next()) return null;
            return resultSet.getString(LocalDatabaseQueryHelper.VALUE_COLUMN);
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setValue(ValueType valueType, String value) throws SetValueException{
        try {
            if (getValue(valueType) == null)
                sqLite.executeUpdate(LocalDatabaseQueryHelper.insertValue(valueType.toString(), value));
            else sqLite.executeUpdate(LocalDatabaseQueryHelper.updateValue(valueType.toString(), value));
        } catch (SQLException e){
            e.printStackTrace();
            throw new SetValueException("Can't set value: "+valueType.toString()+":"+value, e);
        }
    }
}
