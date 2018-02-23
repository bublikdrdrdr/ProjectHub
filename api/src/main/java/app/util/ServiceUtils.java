package app.util;

import java.sql.Timestamp;
import java.util.Date;

public final class ServiceUtils {

    public static Timestamp now() {
        return new Timestamp(new Date().getTime());
    }
}
