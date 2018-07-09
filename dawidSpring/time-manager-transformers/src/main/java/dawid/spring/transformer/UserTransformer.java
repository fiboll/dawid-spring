package dawid.spring.transformer;

import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;
import dawid.spring.model.entity.Task;
import dawid.spring.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by private on 20.01.18.
 */
@Component
public class UserTransformer implements IUserTransformer {

    @Autowired
    private ITaskTransformer taskTransformer;

    @Override
    public UserDTO entityToDTO(User user) {
        UserDTO userDTO = new UserDTO();

        if (user == null) {
            return userDTO;
        }

        userDTO.setFirstName(user.getFirstName());
        userDTO.setId(user.getId());
        userDTO.setNickname(user.getNickname());
        userDTO.setSecondName(user.getSecondName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setMatchingPassword(user.getPassword());
        userDTO.setVersion(user.getVersion());
        for (Task task : user.getTasks()) {
            TaskDTO taskDTO = taskTransformer.entityToDTO(task);
            taskDTO.setUserName(user.getNickname());
            userDTO.getTasks().add(taskDTO);
        }

        return userDTO;
    }

    @Override
    public User update(User user, UserDTO userDTO) {

        user.setFirstName(userDTO.getFirstName());
        user.setId(userDTO.getId());
        user.setNickname(userDTO.getNickname());
        user.setSecondName(userDTO.getSecondName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        for (TaskDTO taskDTO : userDTO.getTasks()) {

            Optional<Task> updateTask = user.getTasks().stream().filter(t -> Objects.equals(t.getId(), taskDTO.getId())).findAny();

            if (!updateTask.isPresent() && taskDTO.getId() != null) {
                throw new IllegalStateException("there is not task with id " + taskDTO.getId());
            }

            Task updatedTask = taskTransformer.updateTask(updateTask.orElse(new Task()), taskDTO);
            user.addTask(updatedTask);
        }

        return user;

    }

    @Override
    public User create(UserDTO accountDto) {
        return update(new User(), accountDto);
    }
}