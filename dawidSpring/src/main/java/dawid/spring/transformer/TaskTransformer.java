package dawid.spring.transformer;

import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskTransformer implements ITaskTransformer {

    public TaskDTO entityToDao(Task task) {

        TaskDTO.TaskBuilder builder = new TaskDTO.TaskBuilder();

        if (task == null) {
            return builder.build();
        }

        builder.desc(task.getDesc());
        builder.isDone(task.isDone());
        builder.dueDate(task.getDueDate());
        builder.name(task.getName());
        builder.username(task.getUser().getNickname());
        builder.version(task.getVersion());
        builder.labels(task.getLabels());
        builder.id(task.getId());

        return builder.build();
    }

    @Override
    public Task updateTask(Task task, TaskDTO dto) {

        task.setDesc(dto.getDesc());
        task.setDone(dto.isDone());
        task.setDueDate(dto.getDueDate());
        task.setName(dto.getName());
        task.setLabels(dto.getLabels());
        return task;
    }

}
