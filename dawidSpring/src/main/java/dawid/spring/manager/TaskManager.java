package dawid.spring.manager;

import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.entity.Label;
import dawid.spring.model.entity.Task;
import dawid.spring.provider.LabelDao;
import dawid.spring.provider.TaskDao;
import dawid.spring.transformer.ITaskTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.List;

@Component
public class TaskManager {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private ITaskTransformer taskTransformer;

    public TaskDTO getTask(Long taskId) {
        return findTaskById(taskId);
    }

    public TaskDTO updateTask(TaskDTO taskDTO) {

        Task updated = taskDao.getTaskById(taskDTO.getId());
        updated.setDone(taskDTO.isDone());
        updated.setDueDate(taskDTO.getDueDate());
        updated.setDesc(taskDTO.getDesc());
        updated.setName(taskDTO.getName());

        taskDao.update(updated);

        return taskTransformer.entityToDao(updated);
    }

    public void deleteTask(Long id) {
        Task deleted = taskDao.getTaskById(id);
        taskDao.removeTask(deleted);
    }

    public List<Label> getAvailableLebels() {
        return labelDao.getAllLabels();
    }

    private TaskDTO findTaskById(Long taskId) {
        try {
            return taskTransformer.entityToDao(taskDao.getTaskById(taskId));
        } catch (NoResultException exception) {
            return null;
        }
    }

}
