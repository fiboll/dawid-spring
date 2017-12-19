package dawid.spring.provider;

import dawid.spring.model.Task;
import dawid.spring.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by dawid on 02.06.17.
 */
public interface UserDAO {

    public Optional<User> getUserById(Long id);

    public void addUser(User user);

    public void removeUser(User user);

    public User update(User user);

    public List<User> findAll();

    public Optional<User> findByNick(String nick);

}
