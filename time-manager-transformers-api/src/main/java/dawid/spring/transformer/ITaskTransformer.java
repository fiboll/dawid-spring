package dawid.spring.transformer;

import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.Task;

public interface ITaskTransformer {
    TaskDTO entityToDTO(Task task);
    Task updateTask(Task task, TaskDTO dto);
}
