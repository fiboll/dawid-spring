package dawid.spring.model;

import javax.persistence.*;

/**
 * Created by private on 24.06.17.
 */
@Entity
@Table(name = "columns")
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TABLE_SEQUENCE")
    @SequenceGenerator(name = "TABLE_SEQUENCE", sequenceName = "TABLE_SEQUENCE", allocationSize = 1)
    private Long id;

    private String title;

    @Version
    private Long version;

    @OneToOne
    private TableColumn backlog;

    @OneToOne
    @Column(name = "next_do_do")
    private TableColumn nextTodo;

    @OneToOne
    private TableColumn doing;

    @OneToOne
    private TableColumn done;
}

