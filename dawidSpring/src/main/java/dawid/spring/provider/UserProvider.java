package dawid.spring.provider;

import dawid.spring.model.User;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Created by dawid on 02.06.17.
 */
public interface UserProvider {

    public Optional<User> getUserById(Long id);

    public void addUser(User user);

    public void removeUser(User user);

    public User update(User user);

    public List<User> findAll();

    public Optional<User> findByNameAndSurname(String firstName, String secondName);

}
