package dawid.spring.transformer;

import dawid.spring.model.dto.LabelDTO;
import dawid.spring.model.Label;

public interface ILabelTransformer {
    LabelDTO entityToDTO(Label label);
    Label dtoToEntity(LabelDTO label);
}
