package dawid.spring.provider;

import dawid.spring.model.User;

/**
 * Created by dawid on 02.06.17.
 */
public interface UserProvider {

    public User getUserById(Long id);

    public void addUser(User user);

    public void removeUser(User user);

    public User update(User user);
}
