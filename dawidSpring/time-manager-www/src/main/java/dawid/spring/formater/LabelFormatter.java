package dawid.spring.formater;

import dawid.spring.model.entity.Label;
import dawid.spring.provider.LabelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class LabelFormatter implements Formatter<Label> {

    @Autowired
    private LabelDao labelDao;

    @Override
    public Label parse(String text, Locale locale) {
        return labelDao.getAllLabels().stream()
                .filter((l)  -> l.getDescription().equals(text))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String print(Label label, Locale locale) {
        return String.valueOf(label.getDescription());
    }
}