package dawid.spring.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Created by dawid on 09.06.17.
 */
@Entity
@Table(name = "labels")
public class Label implements Comparable<Label>{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LABEL_SEQUENCE")
    @SequenceGenerator(name = "LABEL_SEQUENCE", sequenceName = "LABEL_SEQUENCE", allocationSize = 1)
    private Long id;

    String colour;

    String description;

    @ManyToMany(mappedBy = "labels" )
    private Set<Task> tasks;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Override
	public int compareTo(Label other) {

    	return Comparator.comparing((Label label) -> StringUtils.isNumeric(label.description))
    			.thenComparing(Label::getDescription)
    			.compare(this, other);
	}
    
    @Override
    public int hashCode() {
    	return HashCodeBuilder.reflectionHashCode(this, false);
    }
    
    @Override
    public boolean equals(Object obj) {
    	return EqualsBuilder.reflectionEquals(this, obj, false);
    }
    
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
