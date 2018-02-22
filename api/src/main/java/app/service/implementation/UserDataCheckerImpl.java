package app.service.implementation;

import app.exception.InvalidTypeException;
import app.service.DataType;
import app.service.UserDataChecker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Created by Bublik on 22-Dec-17.
 */
@Service
@PropertySource("service.properties")
public class UserDataCheckerImpl implements UserDataChecker {

    private static final Pattern name = Pattern.compile("^[A-Za-z][a-z]+$");
    private static final Pattern email = Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$");
    private static final Pattern nickname = Pattern.compile("^[A-Za-z]+[A-Za-z\\d._]*[A-Za-z]$");

    @Override
    public boolean check(DataType type, String content) {
        if (content==null) return false;
        switch (type) {
            case NAME:
                return name.matcher(content).matches();
            case SURNAME:
                return name.matcher(content).matches();
            case EMAIL:
                return email.matcher(content).matches();
            case NICKNAME:
                return nickname.matcher(content).matches()&&checkNicknameForbidden(content);
            case PASSWORD:
                return content.length() > 6;
            default:
                throw new InvalidTypeException(type);
        }
    }

    @Value("${user.forbidden_usernames}")
    private String[] forbiddenUsernames;

    private boolean checkNicknameForbidden(String nickname){
        return Arrays.stream(forbiddenUsernames).noneMatch(s -> nickname.equals(s)||nickname.equals(s+"s"));
    }
}
