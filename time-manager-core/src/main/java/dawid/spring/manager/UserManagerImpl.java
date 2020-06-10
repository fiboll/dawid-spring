package dawid.spring.manager;

import dawid.spring.exceptions.EmailExistsException;
import dawid.spring.model.dto.ImmutableUserDTO;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;
import dawid.spring.model.User;
import dawid.spring.provider.UserDAO;
import dawid.spring.transformer.IUserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by private on 21.06.17.
 */
@Component("userManager")
@Transactional
public class UserManagerImpl implements UserManager {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private IUserTransformer userTransformer;

    @Override
    public List<UserDTO> getAllUsers() {
        return userDAO.findAll().stream()
                .map(u -> userTransformer.entityToDTO(u))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> findUserByNick(String nick) {
        return userDAO.findByNick(nick)
                .map(userTransformer::entityToDTO);
    }

    @Override
    public UserDTO addTaskToUSer(UserDTO user, TaskDTO task) {
//        task.setUserName(user.getNickname());
        final ImmutableUserDTO userToUpdate = ImmutableUserDTO.builder().from(user).addTasks(task).build();
        return userUpdate(userToUpdate);
    }

    @Override
    public UserDTO userUpdate(UserDTO userDTO) {
        User user = userDAO.findByNick(userDTO.getNickname()).orElse(new User());
        userTransformer.update(user, userDTO);
        User update = userDAO.update(user);
        return userTransformer.entityToDTO(update);
    }

    @Override
    public UserDTO registerNewUserAccount(UserDTO accountDto) throws EmailExistsException {
        User user = userTransformer.create(accountDto);
        user = userDAO.update(user);
        return userTransformer.entityToDTO(user);
    }

}
