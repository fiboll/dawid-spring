package dawid.spring.model;



import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dawid on 02.06.17.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstName;
    private String secondName;
    @Transient
    private List<Task> tasks;

    public User() {}

    private User (UserBuilder builder) {
        id = builder.id;
        firstName = builder.firstName;
        secondName = builder.secondName;
    }

    public void addTask(Task task) {
        if (tasks == null) {
            tasks = new ArrayList<Task>();
        }
        tasks.add(task);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public static final class UserBuilder {

        private Long id;
        private String firstName;
        private String secondName;

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


    public Long getId() {
        return id;
    }

    public List<Task> getUserTasks() {
        return Collections.unmodifiableList(tasks != null ? tasks : Collections.<Task>emptyList());
    }
}
