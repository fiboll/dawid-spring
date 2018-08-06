package dawid.spring.model.dto;

import dawid.spring.comparator.TaskComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.immutables.value.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@Value.Immutable
@Value.Modifiable
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

    @NotEmpty
    public abstract Set<LabelDTO> getLabels();

    public void doneTask() {
        //setDone(true);
    }

    @Override
    public int compareTo(TaskDTO other) {
        return new TaskComparator().compare(this, other);
    }
}
