package dawid.spring.manager;

import dawid.spring.exceptions.DomainException;
import dawid.spring.model.ColumnKind;
import dawid.spring.model.TableColumn;
import dawid.spring.model.Task;
import dawid.spring.model.TaskTable;
import org.springframework.stereotype.Component;

/**
 * Created by dawid on 05.07.17.
 */
@Component("tableToolbox")
public class TableToolbox {

    public TableColumn getTableColumnByKind(TaskTable table, ColumnKind columnKind) {

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

    public void moveTask(TaskTable taskTable, Task task, ColumnKind oldColumn, ColumnKind newColumn) {
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

}
