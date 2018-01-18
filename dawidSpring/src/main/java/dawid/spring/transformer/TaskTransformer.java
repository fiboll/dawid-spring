package dawid.spring.transformer;

import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskTransformer implements ITaskTransformer {

    public TaskDTO entityToDao(Task task) {
        TaskDTO taskDao = new TaskDTO();

        taskDao.setDesc(task.getDesc());
        taskDao.setDone(task.isDone());
        taskDao.setDueDate(task.getDueDate());
        taskDao.setName(task.getName());
        taskDao.setUserName(task.getUser().getNickname());
        taskDao.setVersion(task.getVersion());
        taskDao.setLabels(task.getLabels());
        taskDao.setId(taskDao.getId());

        return taskDao;
    }

}
