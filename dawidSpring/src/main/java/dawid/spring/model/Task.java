package dawid.spring.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import java.util.*;

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
    @JoinColumn(name="USER_ID")
    private User assignedUser;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @ManyToMany
    @JoinTable(name="TASK_LABELS",
            joinColumns = @JoinColumn(name = "TASK_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "LABEL_ID", referencedColumnName = "ID")
    
    )
    @SortNatural
    private SortedSet<Label> labels = new TreeSet<>();

    private Task() {}

    @Override
    public int compareTo(Task other) {

        return Comparator.nullsLast(Comparator.comparing((Task task) -> Objects.isNull(task)))
                .thenComparing(task -> Objects.isNull(task.getLabels()))
//                .thenComparing(task -> compareLabelsList(task))
                .thenComparing(Task::getName)
                .thenComparing(Task::getDesc)
                .compare(this, other);


    }

    //TODO fix me
    private int compareLabelsList(Task other) {

        if (this.getLabels() == null || other.getLabels() == null) {
            return 0;
        }

        Iterator<Label> thisLabels = this.getLabels().iterator();
        Iterator<Label> otherLabels = other.getLabels().iterator();

        while (true) {

            if (!thisLabels.hasNext() && !otherLabels.hasNext()) {
                break;
            }

            int result = Comparator.comparing(Iterator<Label>::hasNext)
                    .thenComparing(Comparator.comparing(Iterator::next))
                    .compare(thisLabels, otherLabels);

            if (result != 0 ) {
                return result;
            }

        }

        return 0;
    }

    @Override
    public int hashCode() {
// TODO fix me    	return HashCodeBuilder.reflectionHashCode(this, false);
        return super.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
    	return EqualsBuilder.reflectionEquals(this, obj, false);
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
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

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        if (!assignedUser.getTasks().contains(this)) {
            assignedUser.addTask(this);
        }
        this.assignedUser = assignedUser;
    }

    public SortedSet<Label> getLabels() {
        return labels;
    }
}
