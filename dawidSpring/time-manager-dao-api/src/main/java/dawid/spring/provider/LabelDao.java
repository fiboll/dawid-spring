package dawid.spring.provider;


import dawid.spring.model.entity.Label;

import java.util.List;

public interface LabelDao {
    List<Label> getAllLabels();
}
