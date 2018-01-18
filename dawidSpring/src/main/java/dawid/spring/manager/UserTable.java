package dawid.spring.manager;

import dawid.spring.config.TableConfig;
import dawid.spring.model.entity.Task;
import dawid.spring.model.entity.User;
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
    public List<Task> getDoneTasks(User user) {
        if (user == null) {
            return Collections.emptyList();
        }
        return user.getTasks().stream()
                   .filter(Task::isDone)
                   .collect(Collectors.toList());
    }

    @Override
    public List<Task> getDoing(User user) {
        return user.getTasks().stream()
            .sorted()
            .filter(t -> !t.isDone())
            .limit(tableConfig.getMaxDoing())
            .collect(Collectors.toList());
    }

    @Override
    public List<Task> getNextToDo(User user) {
        return user.getTasks().stream()
                   .sorted()
                   .filter(t -> !t.isDone())
                   .skip(tableConfig.getMaxDoing())
                   .limit(tableConfig.getMaxNextDo())
                   .collect(Collectors.toList());
    }

    @Override
    public List<Task> getBacklogTask(User user) {
        int itemsToSkip = tableConfig.getMaxDoing() + tableConfig.getMaxNextDo();

        return user.getTasks().stream()
                   .sorted()
                   .filter(t -> !t.isDone())
                   .skip(itemsToSkip)
                   .collect(Collectors.toList());
    }
}
