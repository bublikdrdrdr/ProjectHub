package app.repository.etc;

import app.repository.entity.User;

import java.util.Objects;

public class BookmarkSearchParams extends SearchParams {

    public static final Sort[] sortValues = Sort.values();

    public enum Sort {NONE, REGISTERED, ONLINE, NAME, SURNAME, ADDED}

    public User user;
    public String name;
    public boolean onlineOnly;

    public BookmarkSearchParams(Sort sort, User user, String name, Boolean desc, Integer first, Integer count, Boolean onlineOnly) {
        super(sort.toString(), desc, first, count);
        this.onlineOnly = nullToDefault(onlineOnly, false);
        this.name = name;
        this.user = user;
    }

    public Sort getSort(){
        return Sort.valueOf(sort);
    }
}
