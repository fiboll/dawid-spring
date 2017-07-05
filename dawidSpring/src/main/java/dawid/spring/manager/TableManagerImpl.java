package dawid.spring.manager;

import dawid.spring.exceptions.DomainException;
import dawid.spring.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by private on 01.07.17.
 */
@Component("tableManager")
@Transactional
public class TableManagerImpl implements TableManager{

    private static final Logger logger = Logger.getLogger(TableManagerImpl.class);

    @Autowired
    private TableToolbox tableToolbox;

    @Autowired
    private TableOrderProcessor tableOrderProcessor;

    @Override
    public void doneTask(TaskTable taskTable, Task task) {
        logger.info(String.format("Task %s isDone", task.getName()));
        tableToolbox.moveTask(taskTable, task, ColumnKind.DOING, ColumnKind.DONE);
        tableOrderProcessor.reorderTable(taskTable);
    }

    @Override
    public void addLabel(TaskTable taskTable, Task task, Label label) {
        task.getLabels().add(label);
        tableOrderProcessor.reorderTable(taskTable);
    }

    @Override
    public void removeLabel(TaskTable taskTable, Task task, Label label) {
        if (!task.getLabels().contains(label)) {
            throw new DomainException(String.format("In table %s, task %s doesn't contain label %s",
                    taskTable.getId(), task.getId(), label.getId()));
        }
        task.getLabels().remove(label);
        tableOrderProcessor.reorderTable(taskTable);
    }


}
