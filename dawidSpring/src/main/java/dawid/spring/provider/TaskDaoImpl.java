package dawid.spring.provider;

import dawid.spring.model.Task;
import dawid.spring.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by dawid on 14.07.17.
 */
@Repository("taskDAO")
@Transactional
public class TaskDaoImpl implements TaskDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Task> getTaskById(Long id) {
        try {
            Task result = (Task) em.createNamedQuery("Task.findById")
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void addTask(Task task) {
        em.persist(task);
    }

    @Override
    public Task update(Task task) {
        return em.merge(task);
    }

}
