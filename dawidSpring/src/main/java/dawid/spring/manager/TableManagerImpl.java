package dawid.spring.manager;

import dawid.spring.exceptions.DomainException;
import dawid.spring.model.entity.Label;
import dawid.spring.model.entity.Task;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Created by private on 01.07.17.
 */
@Component("tableManager")
@Transactional
public class TableManagerImpl implements TableManager {

    private static final Logger logger = Logger.getLogger(TableManagerImpl.class);

    @Override
    public void doneTask(Task task) {

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Task: %s is marked as done",  task.getId()));
        }

        task.setDone(true);
    }

    @Override
    public void addLabel(Task task, Label label) {

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Remove label: %s from task: %s",  task.getId(), label.getDescription()));
        }

        task.getLabels().add(label);
    }

    @Override
    public void removeLabel(Task task, Label label) {
        if (!task.getLabels().contains(label)) {
            throw new DomainException(String.format("Task %s doesn't contain label %s",
                    task.getId(), label.getId()));
        }

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Remove label: %s from task: %s",  task.getId(), label.getDescription()));
        }

        task.getLabels().remove(label);
    }
}
