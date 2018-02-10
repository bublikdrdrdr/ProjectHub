package app.service;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Created by Bublik on 22-Dec-17.
 */
@Service
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
                return nickname.matcher(content).matches();
            case PASSWORD:
                return content.length() > 6;
            default:
                throw new RuntimeException("Unknown check type: " + type.name());
        }
    }
}
