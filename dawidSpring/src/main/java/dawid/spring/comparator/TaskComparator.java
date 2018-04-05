package dawid.spring.comparator;

import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.entity.Label;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by private on 01.07.17.
 */
public class TaskComparator implements Comparator<TaskDTO> {

    @Override
    public int compare(TaskDTO task, TaskDTO task2) {
        return Comparator.nullsLast(TaskComparator::compareLabelsList)
                .thenComparing(TaskDTO::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(TaskDTO::getName, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(TaskDTO::getDesc, Comparator.nullsLast(Comparator.naturalOrder()))
                .compare(task, task2);
    }

    private static int compareLabelsList(TaskDTO task, TaskDTO otherTask) {
        Iterator<Label> thisLabels = new TreeSet<>(task.getLabels()).iterator();
        Iterator<Label> otherLabels = new TreeSet<>(otherTask.getLabels()).iterator();

        while (true) {

            if (!thisLabels.hasNext() && !otherLabels.hasNext()) {
                return 0;
            }

            int result = Comparator.comparing(Iterator<Label>::hasNext).reversed()
                    .thenComparing(Iterator::next)
                    .compare(thisLabels, otherLabels);

            if (result != 0 ) {
                return result;
            }
        }
    }
}
