package dawid.spring.comparator;

import dawid.spring.model.Label;
import dawid.spring.model.Task;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by private on 01.07.17.
 */
public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task task, Task task2) {
        return Comparator.nullsLast(TaskComparator::compareLabelsList)
                .thenComparing(Task::getName)
                .thenComparing(Task::getDesc)
                .compare(task, task2);
    }

    private static int compareLabelsList(Task task, Task otherTask) {
        Iterator<Label> thisLabels = new TreeSet<>(task.getLabels()).iterator();
        Iterator<Label> otherLabels = new TreeSet<>(otherTask.getLabels()).iterator();

        while (true) {

            if (!thisLabels.hasNext() && !otherLabels.hasNext()) {
                break;
            }

            int result = Comparator.comparing(Iterator<Label>::next)
                    .thenComparing(Iterator::hasNext)
                    .compare(thisLabels, otherLabels);

            if (result != 0 ) {
                return result;
            }
        }
        return 0;
    }
}
