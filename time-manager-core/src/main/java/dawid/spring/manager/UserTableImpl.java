package dawid.spring.manager;

import dawid.spring.config.TableConfig;
import dawid.spring.exceptions.DomainException;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Primary
public class UserTableImpl implements IUserTable {

    Map<UserDTO, Table> tables = new WeakHashMap<>();

    @Autowired
    private TableConfig tableConfig;

    @Override
    public List<TaskDTO> getDoneTasks(UserDTO user) {
        return prepareTable(user).getDone();
    }

    @Override
    public List<TaskDTO> getDoingTasks(UserDTO user) {
        return prepareTable(user).getDoing();
    }

    @Override
    public List<TaskDTO> getNextToDoTasks(UserDTO user) {
        return prepareTable(user).getNextToDo();
    }

    @Override
    public List<TaskDTO> getBacklogTasks(UserDTO user) {
        return prepareTable(user).getBacklog();
    }

    private Table prepareTable(UserDTO user) {
        if (user == null) {
            throw new DomainException("user can not be null");
        }
        if (user.getTasks() == null) {
            throw new DomainException("user task's ca can not be null");
        }

        var table = tables.computeIfAbsent(user, (u) -> new Table(u, tableConfig));

        System.out.println(table);
        return table;
    }
}

class Table {
    List<TaskDTO> backlog, nextToDo, doing, done;

    public Table(UserDTO userDTO, TableConfig tableConfig) {
        this.backlog = new LimitedList(l -> true, taskDTO -> !taskDTO.isDone());
        this.nextToDo = new LimitedList(list-> list.size() < tableConfig.getMaxNextDo());
        this.doing = new LimitedList(list-> list.size() < tableConfig.getMaxDoing());
        this.done = new LimitedList(l -> true, TaskDTO::isDone);

        final List<List<TaskDTO>> userLists = Stream.of(doing, nextToDo, done, backlog).collect(Collectors.toList());
        userDTO.getTasks().stream().sorted().forEach(taskDTO ->
            addTaskToUserTable(userLists, taskDTO)
        );
    }

    private void addTaskToUserTable(List<List<TaskDTO>> userLists, TaskDTO task) {
        final Iterator<List<TaskDTO>> iterator = userLists.iterator();

        while (iterator.hasNext() && !iterator.next().add(task));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Table{");
        sb.append("backlog=").append(backlog);
        sb.append("\n, nextToDo=").append(nextToDo);
        sb.append("\n, doing=").append(doing);
        sb.append("\n, done=").append(done);
        sb.append('}');
        return sb.toString();
    }

    List<TaskDTO> getBacklog() {
        return Collections.unmodifiableList(backlog);
    }

    List<TaskDTO> getNextToDo() {
        return Collections.unmodifiableList(nextToDo);
    }

    List<TaskDTO> getDoing() {
        return Collections.unmodifiableList(doing);
    }

    List<TaskDTO> getDone() {
        return Collections.unmodifiableList(done);
    }
}


class LimitedList extends ArrayList<TaskDTO> {

    private Predicate<TaskDTO> addCondition;
    private Predicate<List<TaskDTO>> listPredicate;

    LimitedList( Predicate<List<TaskDTO>> listPredicate, Predicate<TaskDTO> addCondition) {
        this(listPredicate);
        this.addCondition = addCondition;
    }

    LimitedList(Predicate<List<TaskDTO>> listPredicate) {
        this.listPredicate = listPredicate;
        this.addCondition = taskDTO -> taskDTO.isDone() == false;
    }

    @Override
    public boolean add(TaskDTO taskDTO) {
        if(addCondition.test(taskDTO) && listPredicate.test(this)) {
            return super.add(taskDTO);
        }
        return false;
    }
}