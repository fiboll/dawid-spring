package dawid.spring.manager;

import dawid.spring.exceptions.DomainException;
import dawid.spring.model.Label;
import dawid.spring.model.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Created by private on 01.07.17.
 */
@Component("tableManager")
@Transactional
public class TableManagerImpl implements TableManager {

    private static final Logger logger = Logger.getLogger(TableManagerImpl.class);

    @Autowired
    private TableToolbox tableToolbox;

    @Autowired
    private TableOrderProcessor tableOrderProcessor;

    @Override
    public void doneTask(Task task) {
        task.setDone(true);
    }

    @Override
    public void addLabel(Task task, Label label) {
        task.getLabels().add(label);
        //tableOrderProcessor.reorderTable(taskTable);
    }

    @Override
    public void removeLabel(Task task, Label label) {
        if (!task.getLabels().contains(label)) {
            throw new DomainException(String.format("Task %s doesn't contain label %s",
                    task.getId(), label.getId()));
        }
        task.getLabels().remove(label);
        //tableOrderProcessor.reorderTable(taskTable);
    }


}
