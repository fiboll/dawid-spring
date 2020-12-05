package dawid.spring.transformer.impl;

import dawid.spring.model.dto.LabelDTO;
import dawid.spring.model.Label;
import dawid.spring.provider.LabelDao;
import dawid.spring.transformer.ILabelTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isNoneEmpty;

@Component
public class LabelTransformer implements ILabelTransformer {

    @Autowired
    private LabelDao labelDao;

    @Override
    public LabelDTO entityToDTO(Label label) {
        final LabelDTO.LabelDTOBuilder builder = LabelDTO.builder()
                    .description(label.getDescription());

        if (isNoneEmpty(label.getColour())) {
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


