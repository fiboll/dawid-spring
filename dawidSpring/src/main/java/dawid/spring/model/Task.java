package dawid.spring.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by private on 31.05.17.
 */
@Entity
@Table(name = "tasks")
public class Task implements Comparable<Task> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TASKS_SEQUENCE")
    @SequenceGenerator(name = "TASKS_SEQUENCE", sequenceName = "TASKS_SEQUENCE", allocationSize = 1)
    private Long id;
    private String name;
    private String desc;

    @Column(name = "DUE_DATE")
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
    private Set<Label> labels;

    private Task() {}

    @Override
    public int compareTo(Task other) {
        return 0;
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

    public void setTableColumn(TableColumn tableColumn) {//throws DomainException {
        if (!tableColumn.getTasks().contains(this)) {
           // throw new DomainException("Table columns don't contain assigned task!");
        }
        this.column = tableColumn;
    }

    public Set<Label> getLabels() {
        return labels;
    }

}
