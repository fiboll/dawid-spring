package dawid.spring.controller;

import dawid.spring.exceptions.EmailExistsException;
import dawid.spring.manager.UserManager;
import dawid.spring.model.dto.ImmutableUserDTO;
import dawid.spring.model.dto.UserDTO;
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

@Controller
public class RegistrationController {

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/user/registration", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDTO userDto = ImmutableUserDTO.builder().build();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public ModelAndView registerUserAccount
            (@ModelAttribute("user") @Valid UserDTO accountDto,
             BindingResult result, WebRequest request, Errors errors) {


        if (!result.hasErrors()) {
            UserDTO registered = createUserAccount(accountDto, result);
            if (registered == null) {
                result.rejectValue("email", "message.regError");
            }
        }

        if (result.hasErrors()) {
            return new ModelAndView("registration", "user", accountDto);
        }
        else {
            return new ModelAndView("successRegister", "user", accountDto);
        }
    }

    private UserDTO createUserAccount(UserDTO accountDto, BindingResult result) {
        try {
            return userManager.registerNewUserAccount(accountDto);
        } catch (EmailExistsException e) {
            return null;
        }
    }
}
