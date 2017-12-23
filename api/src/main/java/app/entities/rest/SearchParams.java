package app.entities.rest;


import app.Properties;

/**
 * Created by Bublik on 23-Dec-17.
 */
public class SearchParams {

    public enum SortBy {NONE, REGISTERED, ONLINE, NAME, SURNAME}

    public String email;
    public String username;
    public String name;
    public boolean exact = false;
    public SortBy sortBy;
    public boolean desc = false;
    public int first = 0;
    public int count = Properties.db.userSearchDefaultListSize;

    public SearchParams(String email, String username, String name, Boolean exact, SortBy sortBy, Boolean desc, Integer first, Integer count) {
        this.email = email;
        this.username = username;
        this.name = name;
        if (exact!=null) this.exact = exact;
        this.sortBy = sortBy;
        if (desc!=null) this.desc = desc;
        if (first!=null && first>=0) this.first = first;
        if (count!=null && count <= Properties.db.userSearchMaxListSize) this.count = count;
    }
}
