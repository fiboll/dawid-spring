package dawid.spring.model.entity;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dawid on 02.06.17.
 */
@Entity
@NamedQueries({
        @NamedQuery(
                name = "User.findAllUsers",
                query = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.tasks tasks"
                        + " LEFT JOIN FETCH tasks.labels"
        ),
        @NamedQuery(
                name = "User.findUserByNick",
                query  = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.tasks tasks"
                            + " LEFT JOIN FETCH tasks.labels"
                            + " WHERE u.nickname = :nick"
        )
})
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQUENCE")
    @SequenceGenerator(name = "USER_SEQUENCE", sequenceName = "USER_SEQUENCE", allocationSize = 1)
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
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    @org.hibernate.validator.constraints.Email
    @Column(nullable = false, unique = true)
    private String email;

    @Version
    @Column(name = "VERSION")
    private Long version;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }


    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getNickname() {
        return nickname;
    }

    public Long getId() {
        return id;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Long getVersion() {
        return version;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}