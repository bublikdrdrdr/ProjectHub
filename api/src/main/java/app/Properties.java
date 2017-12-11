package app;

/**
 * Created by Bublik on 21-Nov-17.
 */
public final class Properties {

    public static final class hibernate{
        public static final String[] packages = new String[]{"app.entities.db"};
        public static final boolean debug = false;
        public static final boolean defaultDatabase = true;
        public static final boolean dropDatabaseOnStart = false;
    }

    public static final class db{
        public static final int nameLength = 50;
        public static final int surnameLength = nameLength;
        public static final int userImageBytes = 5*1024*1024;
        public static final int messageLength = 5000;
        public static final int messageImageBytes = 5*1024*1024;
        public static final int projectContentLength = 90000;
        public static final int projectAttachmentBytes = 50*1024*1024;
        public static final int projectAttachmentTextLength = 50*1024*1024;
    }
}
