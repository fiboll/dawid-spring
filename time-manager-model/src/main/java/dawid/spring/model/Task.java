package dawid.spring.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by private on 31.05.17.
 */
@Entity
@Table(name = "tasks")
@Data
public class Task  {

    @Id
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
    private boolean isDone;

    @ElementCollection
    @CollectionTable(name = "task_labels", joinColumns = @JoinColumn(name = "task_id"), foreignKey = @ForeignKey(name = "tasks_labels_fk"))
    private Set<Label> labels = new HashSet<>();

    @PreRemove
    private void preRemove(){
        if (user.getTasks().contains(this)) {
            user.getTasks().remove(this);
        }
    }

    public void addLabel(Label label) {
        labels.add(label);
    }


    public Date getDueDate() {
        if (dueDate == null) {
            return null;
        }

        return (Date) dueDate.clone();
    }

    public void setUser(User user) {
        if (!user.getTasks().contains(this)) {
           //throw new DomainException("Table columns don't contain assigned task!");
        }
        this.user = user;
    }
}
