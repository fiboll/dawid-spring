package dawid.spring.transformer;

import dawid.spring.model.dto.ImmutableLabelDTO;
import dawid.spring.model.dto.ImmutableUserDTO;
import dawid.spring.model.dto.LabelDTO;
import dawid.spring.model.entity.Label;
import dawid.spring.provider.LabelDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LabelTransformer implements ILabelTransformer {

    @Autowired
    private LabelDao labelDao;

    @Override
    public LabelDTO entityToDTO(Label label) {
        final ImmutableLabelDTO.Builder builder = ImmutableLabelDTO.builder()
                                                              .description(label.getDescription())
                                                              .id(label.getId());
        if (StringUtils.isNoneEmpty(label.getColour())) {
            builder.colour(label.getColour());
        }
        return builder.build();
    }

    @Override
    public Label dtoToEntity(LabelDTO label) {
        return labelDao.getAllLabels().stream()
                .filter(l -> l.getId().equals(label.getId()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("There is no label with desc " + label.getDescription()));
    }
}


