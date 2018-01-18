package dawid.spring.manager;

import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.entity.Task;
import dawid.spring.provider.TaskDao;
import dawid.spring.transformer.ITaskTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskManager {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private ITaskTransformer taskTransformer;

    public TaskDTO getTask(Long TaskId) {
        return taskTransformer.entityToDao(taskDao.getTaskById(TaskId));
    }

    public void updateTask(TaskDTO taskDTO) {

        Task updated = taskDao.getTaskById(taskDTO.getId());
        updated.setDone(taskDTO.isDone());
        updated.setDueDate(taskDTO.getDueDate());
        updated.setDesc(taskDTO.getDesc());
        updated.setName(taskDTO.getName());

        taskDao.update(updated);
    }

    public void deleteTask(Long id) {
        Task deleted = taskDao.getTaskById(id);
        taskDao.removeTask(deleted);
    }

}
