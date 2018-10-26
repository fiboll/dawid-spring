package dawid.spring.model.entity;

import dawid.spring.exceptions.DomainException;
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
        name = "Task.findTaskById",
        query  = "SELECT DISTINCT t FROM Task t FETCH ALL PROPERTIES"
                + " WHERE t.id = :id"
)
@Table(name = "tasks")
public class Task  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TASKS_SEQUENCE")
    @SequenceGenerator(name = "TASKS_SEQUENCE", sequenceName = "TASKS_SEQUENCE", allocationSize = 1)
    private Long id;
    private String name;
    private String desc;

    @Column(name = "DUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Column(name = "is_done")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isDone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="TASK_LABELS",
            joinColumns = @JoinColumn(name = "TASK_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "LABEL_ID", referencedColumnName = "ID")
    
    )
    private Set<Label> labels = new HashSet<>();

    public Long getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public void addLabel(Label label) {
        labels.add(label);
    }

    public Task() {}

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

    public String getUserName() {
        return getUser().getNickname();
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

    public void setVersion(Long version) {
        this.version = version;
    }
}
