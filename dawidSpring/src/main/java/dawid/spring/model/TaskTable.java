package dawid.spring.model;

import javax.persistence.*;

/**
 * Created by private on 24.06.17.
 */
@Entity
@Table(name = "user_table")
public class TaskTable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TABLE_SEQUENCE")
    @SequenceGenerator(name = "TABLE_SEQUENCE", sequenceName = "TABLE_SEQUENCE", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Version
    private Long version;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "backlog")
    private TableColumn backlog;

    @OneToOne
    @JoinColumn(name = "next_do_do")
    private TableColumn nextTodo;

    @OneToOne
    @JoinColumn(name = "doing")
    private TableColumn doing;

    @OneToOne
    @JoinColumn(name = "done")
    private TableColumn done;

    public void addTaskToBacklog(Task task) {
        backlog.addTask(task);
        task.setTableColumn(backlog);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public TableColumn getBacklog() {
        return backlog;
    }

    public TableColumn getNextTodo() {
        return nextTodo;
    }

    public TableColumn getDoing() {
        return doing;
    }

    public TableColumn getDone() {
        return done;
    }
}

