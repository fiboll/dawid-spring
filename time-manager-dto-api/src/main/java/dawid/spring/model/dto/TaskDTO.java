package dawid.spring.model.dto;

import dawid.spring.comparator.TaskComparator;
import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@With
public class TaskDTO implements Comparable<TaskDTO> {
    private Long id;
    private String name;
    private String desc;
    private Date dueDate;
    private String userName;
    private Long version;
    private Set<LabelDTO> labels;
    private boolean isDone;

    public boolean isInProgress() {
        return !isDone();
    }

    @Override
    public int compareTo(TaskDTO other) {
        return TaskComparator.INSTANCE.compare(this, other);
    }
}
