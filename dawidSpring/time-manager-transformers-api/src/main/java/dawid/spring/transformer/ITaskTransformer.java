package dawid.spring.transformer;

import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.entity.Task;

public interface ITaskTransformer {
    TaskDTO entityToDao(Task task);
    Task updateTask(Task task, TaskDTO dto);
}
