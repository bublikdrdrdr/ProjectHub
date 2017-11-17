package app.entities.rest;

/**
 * Created by Bublik on 09-Nov-17.
 */
public class DatabaseParams {
    public String dialect;
    public String url;
    public String user;
    public String password;
    public String driver;

    public DatabaseParams(String dialect, String url, String user, String password, String driver) {
        this.dialect = dialect;
        this.url = url;
        this.user = user;
        this.password = password;
        this.driver = driver;
    }
}
