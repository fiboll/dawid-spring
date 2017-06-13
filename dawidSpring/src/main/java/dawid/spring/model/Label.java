package dawid.spring.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<Task> tasks;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
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
