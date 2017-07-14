package dawid.spring.manager;

import dawid.spring.model.Task;
import dawid.spring.model.User;
import dawid.spring.provider.UserDAO;
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
public class UserManagerImpl implements UserManager {

    @Autowired
    private UserDAO userDAO;

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public Optional<User> findUserByNick(String nick) {
        return userDAO.findByNick(nick);
    }

    @Override
    public void addTaskToUSer(User user, Task task) {
        user.addTask(task);
        userDAO.addTask(task);
    }

}
