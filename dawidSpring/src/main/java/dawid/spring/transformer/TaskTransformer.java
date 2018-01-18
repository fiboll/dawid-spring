package dawid.spring.transformer;

import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskTransformer implements ITaskTransformer {

    public TaskDTO entityToDao(Task task) {
        TaskDTO taskDTO = new TaskDTO();

        if (task == null) {
            return null;
        }

        taskDTO.setDesc(task.getDesc());
        taskDTO.setDone(task.isDone());
        taskDTO.setDueDate(task.getDueDate());
        taskDTO.setName(task.getName());
        taskDTO.setUserName(task.getUser().getNickname());
        taskDTO.setVersion(task.getVersion());
        taskDTO.setLabels(task.getLabels());
        taskDTO.setId(task.getId());

        return taskDTO;
    }

    @Override
    public Task updateTask(Task task, TaskDTO dto) {

        dto.setDesc(task.getDesc());
        dto.setDone(task.isDone());
        dto.setDueDate(task.getDueDate());
        dto.setName(task.getName());
        dto.setUserName(task.getUser().getNickname());
        dto.setVersion(task.getVersion());
        dto.setLabels(task.getLabels());
        dto.setId(task.getId());

        return task;
    }

}
