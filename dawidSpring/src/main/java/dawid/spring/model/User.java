package dawid.spring.model;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dawid on 02.06.17.
 */
@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(
                name = "User.findAll",
                query = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.tasks t LEFT JOIN FETCH t.labels l"),
        @NamedQuery(
                name = "User.findByNick",
                query = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.tasks t LEFT JOIN FETCH t.labels l WHERE u.nickname = :nick")
})
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

    @OneToMany(mappedBy="assignedUser")
    private Set<Task> tasks;

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
    }

    public void addTask(Task task) {
        if (tasks == null) {
            tasks = new HashSet<>();
        }
        tasks.add(task);
        task.setAssignedUser(this);
    }

//    @Override
//    public int hashCode() {
//        return HashCodeBuilder.reflectionHashCode(this, false);
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        return EqualsBuilder.reflectionEquals(this, obj, false);
//    }

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
}
