package dawid.spring.model.dto;

import lombok.Builder;
import lombok.Data;

import static java.util.Comparator.*;
import static org.apache.commons.lang3.StringUtils.isNumeric;

@Data
@Builder
public class LabelDTO implements Comparable<LabelDTO> {

    private String colour;
    private String description;

    @Override
    public int compareTo(LabelDTO other) {
        return comparing((LabelDTO label) -> label.getDescription() != null && isNumeric(label.getDescription()))
                .reversed()
                .thenComparing(LabelDTO::getDescription, nullsLast(naturalOrder()))
                .compare(this, other);
    }
}
