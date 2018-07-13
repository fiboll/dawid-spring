package dawid.spring.provider;

import dawid.spring.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by dawid on 02.06.17.
 */
public interface UserDAO {

    Optional<User> getUserById(Long id);

    void addUser(User user);

    void removeUser(User user);

    User update(User user);

    List<User> findAll();

    Optional<User> findByNick(String nick);

}
