package app.controllers;

import app.Main;
import app.entities.rest.DatabaseParams;
import app.exceptions.SetValueException;
import app.local.LocalSettings;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created by Bublik on 09-Nov-17.
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    private static final int loginLength = 40;

    //last password: wN8e72.OJ2qPfCe6oX3wqeg+rLH))K*C6wKq5f3,H
    @Autowired
    LocalSettings localSettings;

    @RequestMapping(value = "/db", method = RequestMethod.GET)
    public ResponseEntity<DatabaseParams> getDatabaseParams(@RequestParam String password) {
        if (!Main.debug && !checkPassword(password)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(getDatabaseParams());
    }

    @RequestMapping(value = "/db", method = RequestMethod.POST)
    public ResponseEntity<String> setDatabaseParams(@RequestParam String password,
                                                    @RequestParam(required = false) String dialect,
                                                    @RequestParam(required = false) String url,
                                                    @RequestParam(required = false) String user,
                                                    @RequestParam(required = false) String dbpass,
                                                    @RequestParam(required = false) String driver){
        if (!Main.debug && !checkPassword(password)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        try {
            if (dialect!=null) localSettings.setValue(LocalSettings.ValueType.DB_DIALECT, dialect);
            if (url!=null) localSettings.setValue(LocalSettings.ValueType.DB_URL, url);
            if (user!=null) localSettings.setValue(LocalSettings.ValueType.DB_USER, user);
            if (dbpass!=null) localSettings.setValue(LocalSettings.ValueType.DB_PASS, dbpass);
            if (driver!=null) localSettings.setValue(LocalSettings.ValueType.DB_DRIVER, driver);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SetValueException e){
            return new ResponseEntity<>("Can't set value", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public ResponseEntity<String> generatePassword(@RequestParam String old) {
        if (localSettings.getValue(LocalSettings.ValueType.ACCESS_PASS) != null) {
            if (!Main.debug && !checkPassword(old))
                return new ResponseEntity<>("Wrong password", HttpStatus.FORBIDDEN);
        }
        String password = generatePass();
        lastAccept = System.currentTimeMillis();
        lastPassword = password;
        return new ResponseEntity<>(password, HttpStatus.ACCEPTED);
    }

    private static final long submitTimeout = 20000;
    private long lastAccept;
    private String lastPassword;
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public ResponseEntity<String> submitPassword(@RequestParam String password){
        try {
            if (System.currentTimeMillis()-lastAccept>submitTimeout){
                return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
            }
            if (!lastPassword.equals(password)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            localSettings.setValue(LocalSettings.ValueType.ACCESS_PASS, password);
            Logger.getLogger(SystemController.class).log(Logger.Level.WARN, "New password set: "+password);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SetValueException e){
            return new ResponseEntity<>("Can't set value", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private DatabaseParams getDatabaseParams(){
        return new DatabaseParams(localSettings.getValue(LocalSettings.ValueType.DB_DIALECT),
                localSettings.getValue(LocalSettings.ValueType.DB_URL),
                localSettings.getValue(LocalSettings.ValueType.DB_USER),
                localSettings.getValue(LocalSettings.ValueType.DB_PASS),
                localSettings.getValue(LocalSettings.ValueType.DB_DRIVER));
    }

    private boolean checkPassword(String password){
        return password!=null&&password.equals(localSettings.getValue(LocalSettings.ValueType.ACCESS_PASS));
    }

    private String generatePass() {
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        while (password.length() <= loginLength) {
            password.append(getRandomChar(random));
        }
        return password.toString();
    }

    private char getRandomChar(Random random){
        int index = random.nextInt(67);
        index+=40;
        if (index>57) {
            index+=8;
            if (index>90)
                index+=7;
        }
        return (char)index;
    }
}
