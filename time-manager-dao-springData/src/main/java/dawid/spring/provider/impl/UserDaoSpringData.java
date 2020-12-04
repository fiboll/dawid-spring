package dawid.spring.provider.impl;

import dawid.spring.model.User;
import dawid.spring.provider.UserDAO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDaoSpringData extends CrudRepository<User, Long>, UserDAO {

    @Override
    default User update(User user) {
        return save(user);
    }

    @Override
    @Query(name = "User.findUserByNick" )
    Optional<User> findByNick(@Param("nick") String nick);

    @Override
    @Query(name = "User.findAllUsers")
     List<User> findAll();

    @Override
    default void removeUser(User user) {
        delete(user);
    }

    @Override
    default void addUser(User user) {
        save(user);
    }
}
