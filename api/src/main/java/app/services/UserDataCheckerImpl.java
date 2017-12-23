package app.services;

import org.springframework.stereotype.Service;

/**
 * Created by Bublik on 22-Dec-17.
 */
@Service
public class UserDataCheckerImpl implements UserDataChecker {

    @Override
    public boolean check(DataType type, Object content) {
        return false;
    }
}
