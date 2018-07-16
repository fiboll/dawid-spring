package dawid.spring.manager;

import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;

import java.util.List;

/**
 * Created by private on 23.12.17.
 */
public interface IUserTable {

    List<TaskDTO> getDoneTasks(UserDTO user);

    List<TaskDTO> getDoing(UserDTO user);

    List<TaskDTO> getNextToDo(UserDTO user);

    List<TaskDTO> getBacklogTask(UserDTO user);
}
