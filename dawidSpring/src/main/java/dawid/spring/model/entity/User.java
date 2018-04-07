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
    private String email;

    @Version
    @Column(name = "VERSION")
    private Long version;

    public User() {
    }

    private User(UserBuilder builder) {
        id = builder.id;
        firstName = builder.firstName;
        secondName = builder.secondName;
        nickname = builder.nickname;
        tasks = new HashSet<>();
    }

    public void addTask(Task task) {

        if (tasks == null) {
           tasks = new HashSet<>();
        }

        if (!tasks.contains(task)) {
            tasks.add(task);
        }

        task.setUser(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    public static final class UserBuilder {

        private Long id;
        private String firstName;
        private String secondName;
        private String nickname;

        public UserBuilder id(long id) {
            this.id = id;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder secondName(String secondName) {
            this.secondName = secondName;
            return this;
        }

        public UserBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public User build() {
            return new User(this);
        }
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
}