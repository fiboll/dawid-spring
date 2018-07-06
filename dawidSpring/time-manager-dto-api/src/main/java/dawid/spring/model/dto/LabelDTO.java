package dawid.spring.model.dto;

import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;

public class LabelDTO implements Comparable<LabelDTO>{
    private Long id;

    private String colour;

    private String description;

    @Override
    public int compareTo(LabelDTO other) {
        return Comparator.comparing((LabelDTO label) -> StringUtils.isNumeric(label.description))
                .reversed()
                .thenComparing((LabelDTO label) -> label.getDescription())
                .compare(this, other);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
