package app.controllers;

import app.entities.dao.UsersRepository;
import app.entities.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Bublik on 20-Nov-17.
 */
@RestController
public class TestController {

    @Autowired
    UsersRepository usersRepository;

    @RequestMapping("/user")
    public User getUser(@RequestParam long id){
        return usersRepository.getUser(id);
    }
}
