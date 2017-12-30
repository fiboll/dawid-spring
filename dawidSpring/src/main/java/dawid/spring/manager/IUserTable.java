package dawid.spring.manager;

import dawid.spring.model.Task;
import dawid.spring.model.User;

import java.util.List;

/**
 * Created by private on 23.12.17.
 */
public interface IUserTable {

    public List<Task> getDoneTasks(User user);

    public List<Task> getDoing(User user);

    public List<Task> getNextToDo(User user);
}
