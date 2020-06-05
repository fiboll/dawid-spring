package dawid.spring.model;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dawid on 09.06.17.
 */
@Entity
@Table(name = "labels")
public class Label{
    private String colour;

    private String description;

    @OneToMany(mappedBy = "labels")
    private Set<Task> tasks = new HashSet<>();

    public Label() {}

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
}
