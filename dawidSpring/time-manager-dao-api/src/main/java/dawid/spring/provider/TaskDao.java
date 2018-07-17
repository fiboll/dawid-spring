package dawid.spring.provider;

import dawid.spring.model.entity.Task;
/**
 * Created by dawid on 14.07.17.
 */
public interface TaskDao {

    Task getTaskById(Long id);

    void addTask(Task user);

    Task update(Task task);

    void removeTask(Task task);

}
