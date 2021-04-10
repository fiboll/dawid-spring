package dawid.spring.model;
import java.io.Serializable;

import javax.persistence.Embeddable;
import lombok.Data;

/**
 * Created by dawid on 09.06.17.
 */
@Embeddable
@Data
public class Label implements Serializable {
    private static final long serialVersionUID = -5229907763136270527L;
    
    private String colour;
    private String description;
}
