package dawid.spring.manager;

import dawid.spring.model.dto.LabelDTO;
import dawid.spring.model.dto.TaskDTO;

/**
 * Created by private on 01.07.17.
 */
public interface TableManager {

    void doneTask(TaskDTO task);

    void addLabel(TaskDTO task, LabelDTO label);

    void removeLabel(TaskDTO task, LabelDTO label);
    
}
