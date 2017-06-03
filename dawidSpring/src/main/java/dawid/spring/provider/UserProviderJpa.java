package dawid.spring.provider;

import dawid.spring.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by private on 03.06.17.
 */
@Transactional
@Repository("UserProvider")
public class UserProviderJpa implements UserProvider{

    @PersistenceContext
    private EntityManager em;

    public User getUserById(Long id) {
        return em.find(User.class, id);
    }

    public void addUser(User user) {
        em.merge(user);
    }
}
