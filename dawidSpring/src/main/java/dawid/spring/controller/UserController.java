package dawid.spring.controller;

import dawid.spring.model.User;
import dawid.spring.provider.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * Created by dawid on 09.06.17.
 */
@Controller
public class UserController {

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("users", userManager.getAllUsers());
        return "userList";
    }

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String getByNick(@RequestParam String nick, Model model) {

        Optional<User> user= userManager.findUserByNick(nick);

        if (user.isPresent()) {
            model.addAttribute("users",user.get()) ;
            return "userList";
        }
        model.addAttribute("searchNick",nick) ;
        System.out.println(model);
        return "noUser";

    }
}
