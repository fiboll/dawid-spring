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

}
