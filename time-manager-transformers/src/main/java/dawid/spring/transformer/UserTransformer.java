package dawid.spring.transformer;

import dawid.spring.model.dto.ImmutableTaskDTO;
import dawid.spring.model.dto.ImmutableUserDTO;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;
import dawid.spring.model.Task;
import dawid.spring.model.User;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
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
        var builder = ImmutableUserDTO.builder();

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
                        .map(taskDTO -> ImmutableTaskDTO.copyOf(taskDTO).withUserName(user.getNickname()))
                        .collect(toSet());

        return builder.tasks(tasks)
               .build();

    }

    @Override
    public User update(User user, UserDTO userDTO) {

        user.setFirstName(userDTO.getFirstName());
        user.setId(userDTO.getId());
        user.setNickname(userDTO.getNickname());
        user.setSecondName(userDTO.getSecondName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        addTasks(user, userDTO);

        return user;

    }

    private void addTasks(User user, UserDTO userDTO) {
        if (CollectionUtils.isEmpty(userDTO.getTasks())) {
            user.setTasks(Collections.emptySet());
        }
        for (TaskDTO taskDTO : userDTO.getTasks()) {
            Optional<Task> updateTask = user.getTasks().stream().filter(t -> Objects.equals(t.getId(), taskDTO.getId())).findAny();
            Task updatedTask = taskTransformer.updateTask(updateTask.orElse(new Task()), taskDTO);
            user.addTask(updatedTask);
        }
    }

    @Override
    public User create(UserDTO accountDto) {
        return update(new User(), accountDto);
    }
}