package dawid.spring.manager;

import dawid.spring.exceptions.DomainException;
import dawid.spring.model.*;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by private on 01.07.17.
 */
@Component("tableManager")
@Transactional
public class TableManagerImpl implements TableManager{

    @Override
    public void doneTask(TaskTable taskTable, Task task) throws DomainException {
        moveTask(taskTable, task, ColumnKind.DOING, ColumnKind.DONE);
        reorderTable(taskTable);
    }

    private void reorderTable(TaskTable taskTable) {
        for (int i = ColumnKind.values().length; i > 1; i--) {
            reoderColumns(taskTable, ColumnKind.values()[i], ColumnKind.values()[i-1]);
        }
    }

    private void reoderColumns(TaskTable taskTable, ColumnKind leftColumnKind, ColumnKind rightColumnKind) {

        if (rightColumnKind.getMaxTaskInColumn() == Integer.MIN_VALUE) {
            return;
        }

        TableColumn rightColumn = getTableColumnByKind(taskTable, rightColumnKind);

        if (rightColumn.getTasks().size() == rightColumnKind.getMaxTaskInColumn()) {
            return;
        }

        int taskToMoveCount = rightColumnKind.getMaxTaskInColumn() - rightColumn.getTasks().size();

        TableColumn leftColumn = getTableColumnByKind(taskTable, leftColumnKind);

        Set<Task> taskToMove = leftColumn.getTasks().stream()
                .sorted()
                .limit(taskToMoveCount)
                .collect(Collectors.toSet());

        leftColumn.getTasks().removeAll(taskToMove);
        rightColumn.getTasks().addAll(taskToMove);
    }

    @Override
    public void addLabel(TaskTable taskTable, Task task, Label label) throws DomainException {

    }

    @Override
    public void removeLabel(TaskTable taskTable, Task task, Label label) throws DomainException {

    }

    private void moveTask(TaskTable taskTable, Task task, ColumnKind oldColumn, ColumnKind newColumn) {
        TableColumn oldTableColumn = getTableColumnByKind(taskTable, oldColumn);
        TableColumn newTableColumn = getTableColumnByKind(taskTable, newColumn);

        if (oldTableColumn == null) {
            throw new DomainException("oldTableColumn is null");
        }

        if (!oldTableColumn.getTasks().contains(task)) {
            throw new DomainException(String.format("In Table %s, Column %s, doesn't contain %s task",
                    taskTable.getId(), oldTableColumn.getId(), task.getId()));
        }

        if (newTableColumn == null) {
            throw new DomainException("newTableColumn is null");
        }


        oldTableColumn.getTasks().remove(task);
        newTableColumn.getTasks().add(task);
    }

    private TableColumn getTableColumnByKind(TaskTable table, ColumnKind columnKind) {

        if (table == null) {
            throw new DomainException("column kind is table");
        }

        if (columnKind == null) {
            throw new DomainException("column kind is null");
        }

        switch (columnKind) {
            case BACKLOG: return table.getBacklog();
            case DOING: return table.getDoing();
            case DONE: return table.getDone();
            case NEXT_TODO: return table.getNextTodo();
        }

        throw new DomainException("columnKind is unknown");

    }
}
