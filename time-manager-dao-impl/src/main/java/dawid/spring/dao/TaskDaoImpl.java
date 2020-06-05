package dawid.spring.dao;

import dawid.spring.model.Task;
import dawid.spring.provider.TaskDao;
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
            return (Task) em.createNamedQuery("Task.findTaskById")
                    .setParameter("id", id)
                    .getSingleResult();
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
