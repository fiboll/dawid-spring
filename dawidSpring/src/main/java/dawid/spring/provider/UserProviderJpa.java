package dawid.spring.provider;

import dawid.spring.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by private on 03.06.17.
 */
@Transactional(propagation = Propagation.REQUIRED)
@Repository("UserProvider")
public class UserProviderJpa implements UserProvider {

    @PersistenceContext
    private EntityManager em;

    public User getUserById(Long id) {
        return em.find(User.class, id);
    }

    public void addUser(User user) {
        em.persist(user);
    }

    public User update(User user) {
        return em.merge(user);
    }

    public void removeUser(User user) {
        //(em.contains(user))
            em.remove(user);
    }

    public List<User> findAll() {
        return em.createNamedQuery("User.findAll").getResultList();
    }
}
