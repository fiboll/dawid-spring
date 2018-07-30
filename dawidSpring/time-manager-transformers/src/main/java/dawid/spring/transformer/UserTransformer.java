package dawid.spring.transformer;

import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;
import dawid.spring.model.entity.Task;
import dawid.spring.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toSet;

/**
 * Created by private on 20.01.18.
 */
@Component
public class UserTransformer implements IUserTransformer {

    @Autowired
    private ITaskTransformer taskTransformer;

    @Override
    public UserDTO entityToDTO(User user) {
        var builder = new UserDTO.UserBuilder();

        if (user == null) {
            return builder.build();
        }

        builder.firstName(user.getFirstName());
        builder.id(user.getId());
        builder.nickname(user.getNickname());
        builder.secondName(user.getSecondName());
        builder.email(user.getEmail());
        builder.password(user.getPassword());
        builder.matchingPassword(user.getPassword());
        builder.version(user.getVersion());

        var tasks = user.getTasks().stream()
                .map(taskTransformer::entityToDTO)
                .collect(toSet());
        //tasks.forEach(taskDTO -> taskDTO.setUserName(user.getNickname()));

        builder.tasks(tasks);

        return builder.build();
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