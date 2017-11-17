package app.local;

/**
 * Created by Bublik on 09-Nov-17.
 */
public class LocalDatabaseQueryHelper {

    public static final String DB_NAME = "main.db";
    public static final String DB_SETTINGS_TABLE_NAME = "db_settings";
    public static final String ID_COLUMN = "id";
    public static final String KEY_COLUMN = "key";
    public static final String VALUE_COLUMN = "value";
    public static final String CREATE_TABLE_QUERY = "create table if not exists "+DB_SETTINGS_TABLE_NAME+" (" +
            ID_COLUMN + " integer primary key autoincrement, " +
            KEY_COLUMN + " string, " +
            VALUE_COLUMN + " string)";

    public static String selectValue(String key){
        return "select "+VALUE_COLUMN+" from "+DB_SETTINGS_TABLE_NAME+" where "+KEY_COLUMN+" = '"+key+"' limit 1";
    }

    public static String insertValue(String key, String value){
        String s = "insert into "+DB_SETTINGS_TABLE_NAME+"("+KEY_COLUMN+", "+VALUE_COLUMN+") values('"+key+"', '"+value+"')";
        return s;
    }

    public static String updateValue(String key, String value){
        return "update "+DB_SETTINGS_TABLE_NAME +
                " set "+VALUE_COLUMN+" = '"+value+"'"+
                " where "+KEY_COLUMN+" = '"+key+"'";
    }
}
