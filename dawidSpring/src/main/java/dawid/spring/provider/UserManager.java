package dawid.spring.provider;

import dawid.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by private on 21.06.17.
 */
@Component("userManager")
@Transactional
public class UserManager {

    @Autowired
    private UserProvider userProvider;

    public List<User> getAllUsers() {
        User user = new User.UserBuilder()
                .firstName("Jan")
                .secondName("Kowalski")
                .build();
//        userProvider.addUser(user);
//        userProvider.removeUser(user);
        return userProvider.findAll();
    }
}
