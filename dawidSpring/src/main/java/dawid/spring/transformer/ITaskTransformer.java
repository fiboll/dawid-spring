package dawid.spring.transformer;

import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.entity.Task;

public interface ITaskTransformer {

    public TaskDTO entityToDao(Task task);

    public Task updateTask(Task task, TaskDTO dto);

}
