package dawid.spring.provider;

import dawid.spring.model.Task;
/**
 * Created by dawid on 14.07.17.
 */
public interface TaskDao {

    Task getTaskById(Long id);

    void addTask(Task task);

    Task update(Task task);

    void removeTask(Task task);

}
