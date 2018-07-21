package dawid.spring.transformer;

import dawid.spring.model.dto.LabelDTO;
import dawid.spring.model.entity.Label;
import dawid.spring.provider.LabelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LabelTransformer implements ILabelTransformer {

    @Autowired
    private LabelDao labelDao;

    @Override
    public LabelDTO entityToDTO(Label label) {
        return new LabelDTO(label.getId(), label.getDescription(), label.getColour());
    }

    @Override
    public Label dtoToEntity(LabelDTO label) {
        return labelDao.getAllLabels().stream()
                .filter(l -> l.getId().equals(label.getId()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("There is no label with desc " + label.getDescription()));
    }
}


