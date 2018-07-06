package dawid.spring.manager;

import dawid.spring.exceptions.EmailExistsException;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;

import java.util.List;
import java.util.Optional;

/**
 * Created by private on 01.07.17.
 */
public interface UserManager {

    List<UserDTO> getAllUsers();

    Optional<UserDTO> findUserByNick(String nick);

    void addTaskToUSer(UserDTO user, TaskDTO task);

    void userUpdate(UserDTO userDTO);

    UserDTO registerNewUserAccount(UserDTO accountDto) throws EmailExistsException;
}

