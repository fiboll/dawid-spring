package dawid.spring.manager;

import dawid.spring.model.Label;
import dawid.spring.model.Task;

/**
 * Created by private on 01.07.17.
 */
public interface TableManager {

    public void doneTask(TaskTable taskTable, Task task);

    public void addLabel(TaskTable taskTable, Task task, Label label);

    public void removeLabel(TaskTable taskTable, Task task, Label label);
    
}
