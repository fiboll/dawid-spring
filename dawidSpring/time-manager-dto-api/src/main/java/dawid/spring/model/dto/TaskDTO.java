package dawid.spring.model.dto;

import dawid.spring.comparator.TaskComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class TaskDTO implements Comparable<TaskDTO> {

    private Long id;

    @Size(min = 4)
    private String name;

    @Size(min = 4)
    private String desc;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;
    private Long version;
    private boolean isDone;
    private String userName;

    @NotEmpty
    private Set<LabelDTO> labels = new TreeSet<>();

    private static final transient TaskComparator defaultComparator = new TaskComparator();

    public void doneTask() {
        setDone(true);
    }

    private TaskDTO(TaskBuilder taskBuilder) {
        id = taskBuilder.id;
        name = taskBuilder.name;
        desc = taskBuilder.desc;
        dueDate = taskBuilder.dueDate;
        isDone = taskBuilder.isDone;

        if (CollectionUtils.isNotEmpty(taskBuilder.labels)) {
            labels.addAll(taskBuilder.labels);
        }
        userName = taskBuilder.username;
        version = taskBuilder.version;
    }

    public TaskDTO() {}

    @Override
    public int compareTo(TaskDTO other) {
        return defaultComparator.compare(this, other);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }



    public static class TaskBuilder {
        private Long id;
        private String name;
        private String desc;
        private Date dueDate;
        private boolean isDone;
        private final Set<LabelDTO> labels = new HashSet<>();
        private String username;
        private Long version;

        public TaskDTO build() {
            return new TaskDTO(this);
        }

        public TaskBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TaskBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TaskBuilder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public TaskBuilder dueDate(Date dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public TaskBuilder isDone(boolean isDone) {
            this.isDone = isDone;
            return this;
        }

        public TaskBuilder addLabel(LabelDTO label) {
            this.labels.add(label);
            return this;
        }

        public TaskBuilder labels(Set<LabelDTO> labels) {
            if (CollectionUtils.isNotEmpty(labels)) {
                this.labels.addAll(labels);
            }
            return this;
        }

        public TaskBuilder username(String username) {
            this.username = username;
            return this;
        }

        public TaskBuilder version(Long version) {
            this.version = version;
            return this;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getDueDate() {
        if (dueDate == null) {
            return null;
        }

        return (Date) dueDate.clone();
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<LabelDTO> getLabels() {
        return labels;
    }

    public void setLabels(Set<LabelDTO> labels) {
        this.labels = labels;
    }
}
