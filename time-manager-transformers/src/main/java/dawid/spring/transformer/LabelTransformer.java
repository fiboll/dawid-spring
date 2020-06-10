package dawid.spring.transformer;

import dawid.spring.model.dto.ImmutableLabelDTO;
import dawid.spring.model.dto.ImmutableUserDTO;
import dawid.spring.model.dto.LabelDTO;
import dawid.spring.model.Label;
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
                                                              .description(label.getDescription());

        if (StringUtils.isNoneEmpty(label.getColour())) {
            builder.colour(label.getColour());
        }
        return builder.build();
    }

    @Override
    public Label dtoToEntity(LabelDTO label) {
        Label result =  new Label();
        result.setColour(label.getColour());
        result.setDescription(label.getDescription());
        return result;
    }
}


