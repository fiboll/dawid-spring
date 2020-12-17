package dawid.spring.provider.impl;

import dawid.spring.model.User;
import dawid.spring.provider.UserDAO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDaoSpringData extends CrudRepository<User, Long>, UserDAO {

    @Override
    default User update(User user) {
        return save(user);
    }

    @Override
    default void removeUser(User user) {
        delete(user);
    }

    @Override
    default void addUser(User user) {
        save(user);
    }

    @Query("Select u from User u where u.nickname = ?1")
    Optional<User> findByNick(String nick);
}
