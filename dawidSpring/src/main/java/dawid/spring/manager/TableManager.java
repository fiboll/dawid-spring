package dawid.spring.manager;

import dawid.spring.model.Label;
import dawid.spring.model.Task;
import dawid.spring.model.User;

/**
 * Created by private on 01.07.17.
 */
public interface TableManager {

    public void doneTask(User user, Task task);

    public void addLabel(Task task, Label label);

    public void removeLabel(Task task, Label label);
    
}
