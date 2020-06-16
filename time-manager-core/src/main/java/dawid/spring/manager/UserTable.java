package dawid.spring.manager;

import dawid.spring.config.TableConfig;
import dawid.spring.exceptions.DomainException;
import dawid.spring.model.Task;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * Created by private on 23.12.17.
 */
@Component
public class UserTable implements IUserTable {

    @Autowired
    private TableConfig tableConfig;

    @Override
    public List<TaskDTO> getDoneTasks(UserDTO user) {
        return getTasksHelper(user, Integer.MAX_VALUE, 0, TaskDTO::isDone);
    }

    @Override
    public List<TaskDTO> getDoingTasks(UserDTO user) {
        return getTasksHelper(user, tableConfig.getMaxDoing(), 0, TaskDTO::isInProgress);
    }

    @Override
    public List<TaskDTO> getNextToDoTasks(UserDTO user) {
        return getTasksHelper(user, tableConfig.getMaxNextDo(), tableConfig.getMaxDoing(), TaskDTO::isInProgress);
    }

    @Override
    public List<TaskDTO> getBacklogTasks(UserDTO user) {
        int itemsToSkip = tableConfig.getMaxDoing() + tableConfig.getMaxNextDo();
        return getTasksHelper(user, Integer.MAX_VALUE, itemsToSkip, TaskDTO::isInProgress);
    }

    private List<TaskDTO> getTasksHelper(UserDTO user, int limit, int itemsToSkip, Predicate<TaskDTO> predicate) {
        checkPreconditions(user);
        return user.getTasks().stream()
                .sorted()
                .filter(predicate)
                .skip(itemsToSkip)
                .limit(limit)
                .collect(toList());
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
