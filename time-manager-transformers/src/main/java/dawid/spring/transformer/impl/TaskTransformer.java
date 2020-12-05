package dawid.spring.transformer.impl;

import dawid.spring.model.dto.LabelDTO;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.Task;
import dawid.spring.transformer.ILabelTransformer;
import dawid.spring.transformer.ITaskTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TaskTransformer implements ITaskTransformer {

    @Autowired
    private ILabelTransformer labelTransformer;

    public TaskDTO entityToDTO(Task task) {

        TaskDTO.TaskDTOBuilder builder = TaskDTO.builder();

        builder.desc(task.getDesc());
        builder.isDone(task.isDone());
        builder.dueDate(task.getDueDate());
        builder.name(task.getName());
        builder.userName(task.getUser().getNickname());
        builder.version(task.getVersion());
        builder.labels(task.getLabels().stream().map(labelTransformer::entityToDTO).collect(Collectors.toSet()));
        builder.id(task.getId());

        return builder.build();
    }

    @Override
    public Task updateTask(Task task, TaskDTO dto) {

        task.setDesc(dto.getDesc());
        task.setDone(dto.isDone());
        task.setDueDate(dto.getDueDate());
        task.setName(dto.getName());
        task.getLabels().clear();
        for (LabelDTO labelDTO : dto.getLabels()) {
            task.addLabel(labelTransformer.dtoToEntity(labelDTO));
        }

        return task;
    }
}
