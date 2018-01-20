package dawid.spring.manager;

import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;

import java.util.List;
import java.util.Optional;

/**
 * Created by private on 01.07.17.
 */
public interface UserManager {

    public List<UserDTO> getAllUsers();

    public Optional<UserDTO> findUserByNick(String nick);

    public void addTaskToUSer(UserDTO user, TaskDTO task);
}

