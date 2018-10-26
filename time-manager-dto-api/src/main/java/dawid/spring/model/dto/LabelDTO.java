package dawid.spring.model.dto;

import org.immutables.value.Value;

import static java.util.Comparator.*;
import static org.apache.commons.lang3.StringUtils.isNumeric;

@Value.Immutable
@Value.Modifiable
public abstract class LabelDTO implements Comparable<LabelDTO> {

    public abstract Long getId();

    @Value.Default
    public String getColour() {
        return "";
    }

    public abstract String getDescription();

    @Override
    public int compareTo(LabelDTO other) {
        return comparing((LabelDTO label) -> label.getDescription() != null && isNumeric(label.getDescription()))
                .reversed()
                .thenComparing(LabelDTO::getDescription, nullsLast(naturalOrder()))
                .compare(this, other);
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
