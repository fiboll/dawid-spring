package dawid.spring.model;
import javax.persistence.Embeddable;
import lombok.Data;

/**
 * Created by dawid on 09.06.17.
 */
@Embeddable
@Data
public class Label{
    private String colour;
    private String description;
}
