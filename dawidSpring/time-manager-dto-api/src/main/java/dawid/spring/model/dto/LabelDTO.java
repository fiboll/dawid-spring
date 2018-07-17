package dawid.spring.model.dto;

import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;

public class LabelDTO implements Comparable<LabelDTO>{
    private Long id;

    private String colour;

    private String description;

    public LabelDTO() {}

    public LabelDTO(Long id,String description, String colour) {
        this.description = description;
        this.colour = colour;
        this.id = id;
    }

    @Override
    public int compareTo(LabelDTO other) {
        return Comparator.comparing((LabelDTO label) -> StringUtils.isNumeric(label.description))
                .reversed()
                .thenComparing(LabelDTO::getDescription)
                .compare(this, other);
    }

    @Override public String toString() {
        var sb = new StringBuilder("LabelDTO{");
        sb.append("id=").append(id);
        sb.append(", colour='").append(colour).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
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
