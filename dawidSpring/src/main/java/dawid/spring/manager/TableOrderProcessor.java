package dawid.spring.manager;

import dawid.spring.model.ColumnKind;
import dawid.spring.model.TableColumn;
import dawid.spring.model.Task;
import dawid.spring.model.TaskTable;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by dawid on 05.07.17.
 */
@Component("tableOrderProcessor")
public class TableOrderProcessor {

    private static final Logger logger = Logger.getLogger(TableManagerImpl.class);

    @Autowired
    private TableToolbox tableToolbox;

    public void reorderTable(TaskTable taskTable) {
        for (int i = ColumnKind.values().length -1; i > 0; i--) {
            reoderColumns(taskTable, ColumnKind.values()[i-1], ColumnKind.values()[i]);
        }
    }

    public void reoderColumns(TaskTable taskTable, ColumnKind leftColumnKind, ColumnKind rightColumnKind) {
        logger.info(String.format("----reorder table: %s, columns:%s, %s", taskTable.getId(), leftColumnKind, rightColumnKind));
        if (rightColumnKind.getMaxTaskInColumn() == Integer.MAX_VALUE) {
            logger.info(String.format("Column %s hasn't max number of tax", rightColumnKind));
            return;
        }

        TableColumn rightColumn = tableToolbox.getTableColumnByKind(taskTable, rightColumnKind);

        if (rightColumn.getTasks().size() == rightColumnKind.getMaxTaskInColumn()) {
            logger.info(String.format("Column %s is full", rightColumnKind));
            return;
        }

        int taskToMoveCount = rightColumnKind.getMaxTaskInColumn() - rightColumn.getTasks().size();

        logger.info(String.format("Try to move %s from %s to %s", taskToMoveCount, leftColumnKind, rightColumnKind));

        TableColumn leftColumn = tableToolbox.getTableColumnByKind(taskTable, leftColumnKind);

        Set<Task> taskToMove = leftColumn.getTasks().stream()
                .sorted()
                .limit(taskToMoveCount)
                .collect(Collectors.toSet());

        logger.info(String.format("Moved task %s",
                taskToMove.stream()
                        .map(Task::getName)
                        .collect(Collectors.joining(", "))

        ));
        leftColumn.getTasks().removeAll(taskToMove);
        rightColumn.getTasks().addAll(taskToMove);
    }

}
