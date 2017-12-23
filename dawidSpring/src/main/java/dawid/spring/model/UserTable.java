package dawid.spring.model;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by private on 23.12.17.
 */
@Component
public class UserTable implements IUserTable{

    @Override
    public List<Task> getDoneTasks(User user) {
        if (user == null) {
            return Collections.emptyList();
        }
        return user.getTasks().stream()
                   .filter(Task::isDone)
                   .collect(Collectors.toList());
    }
}
