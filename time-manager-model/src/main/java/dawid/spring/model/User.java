package dawid.spring.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements Serializable {

    private static final long serialVersionUID = 3670411905474649253L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name= "second_name")
    private String secondName;

    @Column(nullable = false, unique = true)
    private String nickname;

    @OneToMany(mappedBy="user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<Task> tasks;

    @NotNull
    private String password;

    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @Version
    private long version;

    public User() {
    }

    public void addTask(Task task) {

        if (tasks == null) {
           tasks = new HashSet<>();
        }
        tasks.add(task);
        task.setUser(this);
    }

    @PreRemove
    public void preRemove() {
        tasks.clear();
    }
}