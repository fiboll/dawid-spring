package dawid.spring.model;

import dawid.spring.comparator.TaskComparator;
import dawid.spring.exceptions.DomainException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
        query  = "SELECT DISTINCT t FROM Task t LEFT JOIN FETCH t.labels l"
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
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name="table_column")
    private TableColumn column;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @ManyToMany
    @JoinTable(name="TASK_LABELS",
            joinColumns = @JoinColumn(name = "TASK_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "LABEL_ID", referencedColumnName = "ID")
    
    )
    private Set<Label> labels = new HashSet<>();

    @Transient
    private TaskComparator defaultComparator = new TaskComparator();

    @Override
    public int compareTo(Task other) {
        return defaultComparator.compare(this, other);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    private void addLabel(Label label) {
        if (labels == null) {
            labels = new HashSet<>();
        }
        labels.add(label);
    }

    public Task() {}

    private Task(TaskBuilder taskBuilder) {
        id = taskBuilder.id;
        name = taskBuilder.name;
        desc = taskBuilder.desc;
        dueDate = taskBuilder.dueDate;
    }

    public static class TaskBuilder {
        private Long id;
        private String name;
        private String desc;
        private Date dueDate;

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
        return (Date) dueDate.clone();
    }

    public TableColumn getTaskColumn() {
        return column;
    }

    public void setTableColumn(TableColumn tableColumn) {
        if (!tableColumn.getTasks().contains(this)) {
           throw new DomainException("Table columns don't contain assigned task!");
        }
        this.column = tableColumn;
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

    public TableColumn getColumn() {
        return column;
    }

    public void setColumn(TableColumn column) {
        this.column = column;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }
}
