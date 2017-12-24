package app.controllers;

import app.entities.dao.UsersRepository;
import app.entities.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Bublik on 20-Nov-17.
 */
@RestController
public class TestController {

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@RequestParam String email){
        try {
            User user = usersRepository.getUserByEmail(email);
            if (user==null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(user);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
