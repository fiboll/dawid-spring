package dawid.spring.provider;

import dawid.spring.model.entity.Task;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Created by dawid on 14.07.17.
 */
@Repository("taskDAO")
@Transactional
public class TaskDaoImpl implements TaskDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Task getTaskById(Long id) {
        return em.find(Task.class, id);
    }

    @Override
    public void addTask(Task task) {
        em.persist(task);
    }

    @Override
    public Task update(Task task) {
        return em.merge(task);
    }

    @Override
    public void removeTask(Task task) {
        if(!em.contains(task)) {
            task = em.merge(task);
        }
        task.getUser().getTasks().remove(task);
        em.remove(task);
    }
}
