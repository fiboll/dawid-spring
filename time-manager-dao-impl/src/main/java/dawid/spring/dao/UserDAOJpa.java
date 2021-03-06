package dawid.spring.dao;

import dawid.spring.model.User;
import dawid.spring.provider.UserDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by private on 03.06.17.
 */
@Repository("UserDAO")
@Transactional
public class UserDAOJpa implements UserDAO {

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
        return em.createNamedQuery("User.findAllUsers", User.class).getResultList();
    }

    public Optional<User> findByNick(String nick) {
        try {
            User result = (User) em.createNamedQuery("User.findUserByNick")
                    .setParameter("nick", nick)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
