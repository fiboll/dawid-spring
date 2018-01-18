package dawid.spring.model.entity;

import dawid.spring.comparator.TaskComparator;
import dawid.spring.exceptions.DomainException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by private on 31.05.17.
 */
@Entity
@NamedQuery(
        name = "Task.findById",
        query  = "SELECT DISTINCT t FROM Task t FETCH ALL PROPERTIES"
                + " WHERE t.id = :id"
)
@Table(name = "tasks")
public class Task implements Comparable<Task> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TASKS_SEQUENCE")
    @SequenceGenerator(name = "TASKS_SEQUENCE", sequenceName = "TASKS_SEQUENCE", allocationSize = 1)
    private Long id;
    private String name;
    private String desc;

    @Column(name = "DUE_DATE")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Column(name = "is_done")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isDone;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="TASK_LABELS",
            joinColumns = @JoinColumn(name = "TASK_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "LABEL_ID", referencedColumnName = "ID")
    
    )
    private Set<Label> labels = new HashSet<>();

    public Long getVersion() {
        return version;
    }

    @Transient
    private TaskComparator defaultComparator = new TaskComparator();

    @Override
    public int compareTo(Task other) {
        return defaultComparator.compare(this, other);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public void addLabel(Label label) {
        labels.add(label);
    }

    public Task() {}

    private Task(TaskBuilder taskBuilder) {
        id = taskBuilder.id;
        name = taskBuilder.name;
        desc = taskBuilder.desc;
        dueDate = taskBuilder.dueDate;
        isDone = taskBuilder.isDone;

        if (CollectionUtils.isNotEmpty(labels)) {
            labels.addAll(taskBuilder.labels);
        }
    }

    public static class TaskBuilder {
        private Long id;
        private String name;
        private String desc;
        private Date dueDate;
        private boolean isDone;
        private HashSet<Label> labels;

        public Task build() {
            return new Task(this);
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
    }

    public void doneTask() {
        setDone(true);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Date getDueDate() {
        if (dueDate == null) {
            return null;
        }

        return (Date) dueDate.clone();
    }

    public void setUser(User user) {
        if (!user.getTasks().contains(this)) {
           throw new DomainException("Table columns don't contain assigned task!");
        }
        this.user = user;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    public User getUser() {
        return user;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
