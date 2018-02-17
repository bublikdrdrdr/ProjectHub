package app.repository.etc;

public class UserSearchParams extends SearchParams {

    public enum Sort {NONE, REGISTERED, ONLINE, NAME, SURNAME};

    public String email;
    public String username;
    public String name;
    public boolean onlineOnly = false;
    public boolean exact = false;

    public UserSearchParams(Boolean exact, Sort sort, Boolean desc, Integer first, Integer count, String email, String username, String name, Boolean onlineOnly) {
        super(sort.toString(), desc, first, count);
        if (exact!=null) this.exact = exact;
        if (onlineOnly!=null) this.onlineOnly = onlineOnly;
        this.email = email;
        this.username = username;
        this.name = name;
    }

    public Sort getSort(){
        return Sort.valueOf(sort);
    }
}
