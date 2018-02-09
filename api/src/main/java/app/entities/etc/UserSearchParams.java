package app.entities.etc;

public class UserSearchParams extends SearchParams {

    public enum Sort {NONE, REGISTERED, ONLINE, NAME, SURNAME};

    public String email;
    public String username;
    public String name;
    public boolean exact = false;

    public UserSearchParams(Boolean exact, Sort sort, Boolean desc, Integer first, Integer count, String email, String username, String name) {
        super(sort.toString(), desc, first, count);
        if (exact!=null) this.exact = exact;
        this.email = email;
        this.username = username;
        this.name = name;
    }

    public Sort getSort(){
        return Sort.valueOf(sort);
    }
}
