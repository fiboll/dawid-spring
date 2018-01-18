package dawid.spring.manager;

import dawid.spring.model.entity.Label;
import dawid.spring.model.entity.Task;

/**
 * Created by private on 01.07.17.
 */
public interface TableManager {

    public void doneTask(Task task);

    public void addLabel(Task task, Label label);

    public void removeLabel(Task task, Label label);
    
}
