package dawid.spring.manager;

import dawid.spring.model.Task;
import dawid.spring.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by private on 01.07.17.
 */
public interface UserManager {

    public List<User> getAllUsers();

    public Optional<User> findUserByNick(String nick);

    public void addTaskToUSer(User user, Task task);
}

