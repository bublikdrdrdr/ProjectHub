package app.repository.etc;

import app.repository.entity.User;

public class BlockSearchParams extends SearchParams {

    public User admin;
    public User user;
    public boolean activeOnly = false;

    public BlockSearchParams(String sort, Boolean desc, Integer first, Integer count, User admin, User user, Boolean activeOnly) {
        super(sort, desc, first, count);
        if (activeOnly!=null) this.activeOnly = activeOnly;
        this.admin = admin;
        this.user = user;
    }
}
