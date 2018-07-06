package dawid.spring.manager;

import dawid.spring.model.dto.TaskDTO;

/**
 * Created by private on 01.07.17.
 */
public interface TableManager {

    void doneTask(TaskDTO task);

    void addLabel(TaskDTO task, TaskDTO label);

    void removeLabel(TaskDTO task, TaskDTO label);
    
}
