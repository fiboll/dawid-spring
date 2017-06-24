package dawid.spring.provider;

import dawid.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by private on 21.06.17.
 */
@Component("userManager")
@Transactional
public class UserManager {

    @Autowired
    private UserDAO userDAO;

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public Optional<User> findUserByNick(String nick) {
        return userDAO.findByNick(nick);
    }
}
