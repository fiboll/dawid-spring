package dawid.spring.rest;

import dawid.spring.manager.UserManager;
import dawid.spring.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Dawid Strembicki Hycom (dawid.strembicki.hycom@hycom.pl)
 */
@RestController
@RequestMapping(value = "/users")
public class UserRestController {

    @Autowired
    private UserManager userManager;

    @GetMapping("/all")
    public List<UserDTO> getAllUser() {
        System.out.println(userManager.getAllUsers());
        return userManager.getAllUsers();
    }
}
