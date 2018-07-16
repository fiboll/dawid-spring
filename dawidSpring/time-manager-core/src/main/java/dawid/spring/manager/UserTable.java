package dawid.spring.manager;

import dawid.spring.config.TableConfig;
import dawid.spring.exceptions.DomainException;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * Created by private on 23.12.17.
 */
@Component
public class UserTable implements IUserTable {

    @Autowired
    TableConfig tableConfig;

    @Override
    public List<TaskDTO> getDoneTasks(UserDTO user) {
        checkPreconditions(user);
        return user.getTasks().stream()
                   .filter(TaskDTO::isDone)
                   .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getDoingTasks(UserDTO user) {
        checkPreconditions(user);
        return user.getTasks().stream()
            .sorted()
            .filter(t -> !t.isDone())
            .limit(tableConfig.getMaxDoing())
            .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getNextToDoTasks(UserDTO user) {
        checkPreconditions(user);
        return user.getTasks().stream()
                   .sorted()
                   .filter(t -> !t.isDone())
                   .skip(tableConfig.getMaxDoing())
                   .limit(tableConfig.getMaxNextDo())
                   .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getBacklogTasks(UserDTO user) {
        checkPreconditions(user);
        int itemsToSkip = tableConfig.getMaxDoing() + tableConfig.getMaxNextDo();
        return user.getTasks().stream()
                   .sorted()
                   .filter(t -> !t.isDone())
                   .skip(itemsToSkip)
                   .collect(Collectors.toList());
    }

    private void checkPreconditions(UserDTO user) {
        if (user == null) {
            throw new DomainException("user can not be null");
        }
        if (user.getTasks() == null) {
            throw new DomainException("user task's ca can not be null");
        }
    }
}
