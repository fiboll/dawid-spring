package dawid.spring.manager;

import dawid.spring.config.TableConfig;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by private on 23.12.17.
 */
@Component
public class UserTable implements IUserTable {

    @Autowired
    TableConfig tableConfig;

    @Override
    public List<TaskDTO> getDoneTasks(UserDTO user) {
        if (user == null) {
            return Collections.emptyList();
        }
        return user.getTasks().stream()
                   .filter(TaskDTO::isDone)
                   .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getDoing(UserDTO user) {
        return user.getTasks().stream()
            .sorted()
            .filter(t -> !t.isDone())
            .limit(tableConfig.getMaxDoing())
            .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getNextToDo(UserDTO user) {
        return user.getTasks().stream()
                   .sorted()
                   .filter(t -> !t.isDone())
                   .skip(tableConfig.getMaxDoing())
                   .limit(tableConfig.getMaxNextDo())
                   .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getBacklogTask(UserDTO user) {
        int itemsToSkip = tableConfig.getMaxDoing() + tableConfig.getMaxNextDo();

        return user.getTasks().stream()
                   .sorted()
                   .filter(t -> !t.isDone())
                   .skip(itemsToSkip)
                   .collect(Collectors.toList());
    }
}
