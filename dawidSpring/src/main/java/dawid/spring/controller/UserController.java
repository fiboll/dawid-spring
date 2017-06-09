package dawid.spring.controller;

import dawid.spring.provider.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by dawid on 09.06.17.
 */
@Controller
public class UserController {

    @Autowired
    private UserProvider userProvider;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("users", userProvider.findAll());
        System.out.println(model);
        return "userList";
    }
}
