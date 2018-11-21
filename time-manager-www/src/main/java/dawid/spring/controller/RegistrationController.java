package dawid.spring.controller;

import dawid.spring.exceptions.EmailExistsException;
import dawid.spring.manager.UserManager;
import dawid.spring.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;

import static java.util.Collections.emptySet;

@Controller
public class RegistrationController {

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/user/registration", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        model.addAttribute("user", prepareEmptyUser());
        return "registration";
    }

    private ModifiableUserDTO prepareEmptyUser() {
        return ModifiableUserDTO.create()
                .setEmail("")
                .setFirstName("")
                .setId(1L)
                .setVersion(1L)
                .setTasks(Collections.emptyList())
                .setNickname("")
                .setSecondName("")
                .setPassword("")
                .setMatchingPassword("");
    }

    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public ModelAndView registerUserAccount
            (@Valid @ModelAttribute("user") ModifiableUserDTO user,
             BindingResult result, Model model) {

        if (!result.hasErrors()) {
            UserDTO registered = createUserAccount(user, result);
            if (registered == null) {
                result.rejectValue("email", "message.regError");
            }
        }

        if (result.hasErrors()) {
            return new ModelAndView("registration", "user", user);
        }
        else {
            model.addAttribute("newTask", prepareNewTask(user.getNickname()));
            return new ModelAndView("userDetails", "user", user);
        }
    }

    private UserDTO createUserAccount(UserDTO accountDto, BindingResult result) {
        try {
            return userManager.registerNewUserAccount(accountDto);
        } catch (EmailExistsException e) {
            return null;
        }
    }

    //TODO extract
    private ModifiableTaskDTO prepareNewTask(String userName) {
        var task = ImmutableTaskDTO.builder().userName(userName)
                .name("")
                .done(false)
                .dueDate(new Date())
                .desc("")
                .version(1L)
                .id(1L)
                .labels(emptySet())
                .build();

        return ModifiableTaskDTO.create().from(task);
    }
}
