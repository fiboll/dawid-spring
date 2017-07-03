package dawid.spring.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by private on 24.06.17.
 */
@Entity
@Table(name = "table_columns")
public class TableColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COLUMNS_SEQUENCE")
    @SequenceGenerator(name = "COLUMNS_SEQUENCE", sequenceName = "COLUMNS_SEQUENCE", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String title;

//    @Version
//    private Long version;

    @OneToMany(mappedBy="column")
    private Set<Task> tasks;

    protected void addTask(Task task) {
        tasks.add(task);
    }

    public Set<Task> getTasks() {
        return tasks;
    }

	public String getTitle() {
		return title;
	}

    public Long getId() {
        return id;
    }
}
