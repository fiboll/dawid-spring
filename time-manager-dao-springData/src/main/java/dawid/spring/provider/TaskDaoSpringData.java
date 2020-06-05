package dawid.spring.provider;

import dawid.spring.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
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
