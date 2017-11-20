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
    public String hbm2ddlAuto;
    public String globallyQuotedIdentifiers;

    public DatabaseParams(String dialect, String url, String user, String password, String driver, String hbm2ddlAuto, String globallyQuotedIdentifiers) {
        this.dialect = dialect;
        this.url = url;
        this.user = user;
        this.password = password;
        this.driver = driver;
        this.hbm2ddlAuto = hbm2ddlAuto;
        this.globallyQuotedIdentifiers = globallyQuotedIdentifiers;
    }
}
