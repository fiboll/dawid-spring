package dawid.spring.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

/**
 * Created by dawid on 09.06.17.
 */
@Entity
@Table(name = "labels")
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LABEL_SEQUENCE")
    @SequenceGenerator(name = "LABEL_SEQUENCE", sequenceName = "LABEL_SEQUENCE", allocationSize = 1)
    private Long id;

    String colour;

    String description;

    @ManyToMany(mappedBy = "labels")
    private Collection<Task> tasks;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
