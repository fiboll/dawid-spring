package dawid.spring.provider;

import dawid.spring.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Created by private on 03.06.17.
 */
@Transactional
@Repository("UserProvider")
public class UserProviderJpa implements UserProvider {

    @PersistenceContext
    private EntityManager em;

    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    public void addUser(User user) {
        em.persist(user);
    }

    public User update(User user) {
        return em.merge(user);
    }

    public void removeUser(User user) {
        em.remove(user);
    }

    public List<User> findAll() {
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }

    public Optional<User> findByNameAndSurname(String firstName, String secondName) {
         User result= (User) em.createNamedQuery("User.findByNameAndSurname")
                .setParameter("firstName", firstName)
                .setParameter("secondName", secondName)
                .getSingleResult();
         return Optional.ofNullable(result);
    }
}
