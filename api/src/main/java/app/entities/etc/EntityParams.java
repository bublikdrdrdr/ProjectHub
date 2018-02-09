package app.entities.etc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

public final class EntityParams {

    public static final int nameLength = 50;
    public static final int surnameLength = nameLength;
    private static final int mb = 1024*1024;
    public static final int userImageBytes = 5*mb;
    public static final int messageLength = 5000;
    public static final int messageImageBytes = 5*mb;
    public static final int projectContentLength = 90000;
    public static final int projectAttachmentBytes = 50*mb;
    public static final int projectAttachmentTextLength = 50*mb;
}
