package dawid.spring.transformer;

import dawid.spring.model.dto.LabelDTO;
import dawid.spring.model.entity.Label;

public interface ILabelTransformer {
    LabelDTO entityToDTO(Label label);
    Label dTOToEntity(LabelDTO label);
}
