package dawid.spring.formater;

import dawid.spring.model.dto.ImmutableLabelDTO;
import dawid.spring.model.dto.LabelDTO;
import dawid.spring.provider.LabelDao;
import dawid.spring.transformer.LabelTransformer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LabelFormatter implements Formatter<LabelDTO> {

    private static final Logger logger = Logger.getLogger(LabelFormatter.class);

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private LabelTransformer labelTransformer;

    @Override
    public LabelDTO parse(String text, Locale locale) {
        logger.info(String.format("Create label from %s", text));

        return labelDao.getAllLabels().stream()
                .filter((l)  -> l.getDescription().equals(text))
                .findFirst()
                .map(labelTransformer::entityToDTO)
                .orElse(null);
    }

    @Override
    public String print(LabelDTO label, Locale locale) {
        return label.getDescription();
    }
}