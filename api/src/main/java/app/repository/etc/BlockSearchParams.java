package app.repository.etc;

import app.repository.entity.User;
import com.sun.istack.internal.NotNull;

public class BlockSearchParams extends SearchParams {

    public User admin;
    public User user;
    public boolean activeOnly;

    public BlockSearchParams(Boolean desc, Integer first, Integer count, User admin, User user, Boolean activeOnly) {
        super(null, desc, first, count);
        this.activeOnly = nullToDefault(activeOnly, false);
        this.admin = admin;
        this.user = user;
    }
}
