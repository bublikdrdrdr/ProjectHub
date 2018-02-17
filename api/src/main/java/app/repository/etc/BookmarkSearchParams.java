package app.repository.etc;

import app.repository.entity.User;

import java.util.Objects;

public class BookmarkSearchParams extends SearchParams {

    public enum Sort {NONE, REGISTERED, ONLINE, NAME, SURNAME, ADDED}

    public User user;
    public String name;
    public boolean onlineOnly;

    protected BookmarkSearchParams(Sort sort, User user, Boolean desc, Integer first, Integer count, Boolean onlineOnly) {
        super(sort.toString(), desc, first, count);
        this.onlineOnly = nullToDefault(onlineOnly, false);
        this.user = user;
    }

    public Sort getSort(){
        return Sort.valueOf(sort);
    }
}
