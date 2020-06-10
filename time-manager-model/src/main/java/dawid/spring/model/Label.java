package dawid.spring.model;
import javax.persistence.*;

/**
 * Created by dawid on 09.06.17.
 */
@Embeddable
public class Label{
    private String colour;

    private String description;

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
