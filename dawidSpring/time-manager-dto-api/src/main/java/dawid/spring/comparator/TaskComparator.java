package dawid.spring.comparator;

import dawid.spring.model.dto.LabelDTO;
import dawid.spring.model.dto.TaskDTO;

import java.util.Comparator;
import java.util.Iterator;

import static java.util.Comparator.*;

/**
 * Created by private on 01.07.17.
 */
public class TaskComparator implements Comparator<TaskDTO> {

    @Override
    public int compare(TaskDTO task, TaskDTO task2) {
        return nullsLast(TaskComparator::compareLabelsList)
                .thenComparing(TaskDTO::getDueDate, nullsLast(naturalOrder()))
                .thenComparing(TaskDTO::getName, nullsLast(naturalOrder()))
                .thenComparing(TaskDTO::getDesc, nullsLast(naturalOrder()))
                .compare(task, task2);
    }

    private static int compareLabelsList(TaskDTO task, TaskDTO otherTask) {
        var thisLabelsIterator = task.getLabels().iterator();
        var otherLabelsIterator = otherTask.getLabels().iterator();

        while (hasMoreLabels(thisLabelsIterator, otherLabelsIterator)) {
            int result = comparing(Iterator<LabelDTO>::hasNext).reversed()
                    .thenComparing(Iterator::next)
                    .compare(thisLabelsIterator, otherLabelsIterator);
            if (result != 0 ) {
                return result;
            }
        }
        return  0;
    }

    private static boolean hasMoreLabels(Iterator<LabelDTO> thisLabelsIterator, Iterator<LabelDTO> otherLabelsIterator) {
        return thisLabelsIterator.hasNext() || otherLabelsIterator.hasNext();
    }
}