package dawid.spring.provider.impl;

import dawid.spring.model.Task;
import dawid.spring.provider.TaskDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDaoSpringData extends JpaRepository<Task, Long>, TaskDao {

    @Override
    @Modifying
    default void removeTask(Task task) {
        delete(task);
    }


    @Override
    default void addTask(Task task) {
        save(task);
    }

    @Override
    default Task update(Task task) {
        return save(task);
    }
}
