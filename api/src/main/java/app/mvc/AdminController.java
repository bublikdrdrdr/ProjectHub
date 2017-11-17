package app.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Bublik on 10-Nov-17.
 */
@Controller
public class AdminController {
    @RequestMapping("/")
    public String manageAdmin(Model model) {
        return "admin";
    }
}