package dawid.spring.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dawid on 09.06.17.
 */
@Entity
@Table(name = "labels")
public class Label{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LABEL_SEQUENCE")
    @SequenceGenerator(name = "LABEL_SEQUENCE", sequenceName = "LABEL_SEQUENCE", allocationSize = 1)
    private Long id;

    private String colour;

    private String description;

    @ManyToMany(mappedBy = "labels")
    private Set<Task> tasks = new HashSet<>();

    @Version
    @Column(name = "VERSION")
    private Long version;

    public Label() {}
    
//    @Override
//    public int hashCode() {
//    	return HashCodeBuilder.reflectionHashCode(this, false);
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//    	return EqualsBuilder.reflectionEquals(this, obj, false);
//    }
    
    @Override
    public String toString() {
        return description;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
