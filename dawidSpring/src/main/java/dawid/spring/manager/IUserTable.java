package dawid.spring.manager;

import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;

import java.util.List;

/**
 * Created by private on 23.12.17.
 */
public interface IUserTable {

    public List<TaskDTO> getDoneTasks(UserDTO user);

    public List<TaskDTO> getDoing(UserDTO user);

    public List<TaskDTO> getNextToDo(UserDTO user);

    public List<TaskDTO> getBacklogTask(UserDTO user);
}
