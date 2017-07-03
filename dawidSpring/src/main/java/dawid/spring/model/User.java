package dawid.spring.model;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * Created by dawid on 02.06.17.
 */
@Entity
@NamedQueries({
        @NamedQuery(
                name = "User.findAll",
                query = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.table table"
                        + " LEFT JOIN FETCH table.backlog backlog"
                            +  " LEFT JOIN FETCH backlog.tasks backlogTasks"
                            +  " LEFT JOIN FETCH backlogTasks.labels backlogTasksLabels"
                        + " LEFT JOIN FETCH table.nextTodo nextTodo"
                            +  " LEFT JOIN FETCH nextTodo.tasks nextTodoTasks"
                            +  " LEFT JOIN FETCH nextTodoTasks.labels nextTodoTasksLabels"
                        + " LEFT JOIN FETCH table.doing doing"
                            +  " LEFT JOIN FETCH doing.tasks doingTasks"
                            +  " LEFT JOIN FETCH doingTasks.labels doingTasksLabels"
                        + " LEFT JOIN FETCH table.done done"
                            +  " LEFT JOIN FETCH done.tasks doneTasks"
                            +  " LEFT JOIN FETCH doneTasks.labels doneTasksLabels"
        ),
        @NamedQuery(
                name = "User.findByNick",
                query = "SELECT DISTINCT u FROM User u WHERE u.nickname = :nick")
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

    @OneToOne()
    private TaskTable table;

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
        table.addTaskToBacklog(task);
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

    public TaskTable getTable() {
        return table;
    }

}