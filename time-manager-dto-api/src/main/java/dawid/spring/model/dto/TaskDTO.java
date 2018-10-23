package dawid.spring.model.dto;

import dawid.spring.comparator.TaskComparator;
import org.immutables.value.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Value.Immutable
@Value.Modifiable
@Value.Style(get = {"is*", "get*"})
public abstract class TaskDTO implements Comparable<TaskDTO> {
    public abstract Long getId();

    @Size(min = 4)
    public abstract String getName();

    @Size(min = 4)
    public abstract String getDesc();
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public abstract Date getDueDate();
    public abstract Long getVersion();
    public abstract boolean isDone();
    public abstract String getUserName();

    @org.hibernate.validator.constraints.NotEmpty
    public abstract Set<LabelDTO> getLabels();

    @Override
    public int compareTo(TaskDTO other) {
        return new TaskComparator().compare(this, other);
    }
}
