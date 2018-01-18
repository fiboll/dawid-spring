package dawid.spring.provider;

import dawid.spring.model.entity.Task;

import java.util.Optional;

/**
 * Created by dawid on 14.07.17.
 */
public interface TaskDao {

    public Task getTaskById(Long id);

    public void addTask(Task user);

    public Task update(Task task);

    public void removeTask(Task task);

}
