package dawid.spring.manager;

import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.Label;
import dawid.spring.model.Task;
import dawid.spring.provider.LabelDao;
import dawid.spring.provider.TaskDao;
import dawid.spring.transformer.ITaskTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

@Component
@Transactional
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

        Task task = taskDao.getTaskById(taskDTO.getId());
        taskTransformer.updateTask(task, taskDTO);

        final Task updated = taskDao.update(task);

        return taskTransformer.entityToDTO(updated);
    }

    public void deleteTask(Long id) {
        Task deleted = taskDao.getTaskById(id);
        taskDao.removeTask(deleted);
    }

    public List<Label> getAvailableLabels() {
        return labelDao.getAllLabels();
    }

    private TaskDTO findTaskById(Long taskId) {
        try {
            final Task task = taskDao.getTaskById(taskId);
            if (task == null) {
                return null;
            }
            return taskTransformer.entityToDTO(task);
        } catch (NoResultException exception) {
            return null;
        }
    }

}
