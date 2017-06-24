package dawid.spring.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by private on 24.06.17.
 */
@Entity
@Table(name = "columns")
public class TableColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COLUMNS_SEQUENCE")
    @SequenceGenerator(name = "COLUMNS_SEQUENCE", sequenceName = "COLUMNS_SEQUENCE", allocationSize = 1)
    private Long id;

    private String title;

    @Version
    private Long version;

    @OneToMany(mappedBy="taskColumn")
    private Set<Task> tasks;

}
