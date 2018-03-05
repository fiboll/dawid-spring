package dawid.spring.model.dto;

import dawid.spring.comparator.TaskComparator;
import dawid.spring.model.entity.Label;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Transient;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TaskDTO implements Comparable<TaskDTO> {

    private Long id;
    private String name;
    private String desc;
    private Date dueDate;
    private Long version;
    private boolean isDone;
    private String userName;
    private Set<Label> labels = new HashSet<>();
    @Transient
    private TaskComparator defaultComparator = new TaskComparator();

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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public static class TaskBuilder {
        private Long id;
        private String name;
        private String desc;
        private Date dueDate;
        private boolean isDone;
        private Set<Label> labels = new HashSet<>();
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

        public TaskBuilder addLabel(Label label) {
            this.labels.add(label);
            return this;
        }

        public TaskBuilder labels(Set<Label> labels) {
            this.labels.addAll(labels);
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

    public Set<Label> getLabels() {
        return labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }
}
