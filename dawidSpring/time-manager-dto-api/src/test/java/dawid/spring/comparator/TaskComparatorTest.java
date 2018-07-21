package dawid.spring.comparator;

import dawid.spring.model.dto.LabelDTO;
import dawid.spring.model.dto.TaskDTO;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by private on 27.01.18.
 */
public class TaskComparatorTest {

    private TaskDTO t1,t2,t3, tDate1, tDate2, tName1, tName2;
    private LabelDTO a,b;

    private List<TaskDTO> tasks;

    @Before
    public void setup() {

        a = new LabelDTO(1L,"a", "red");
        b = new LabelDTO(2L,"b", "yellow");

        t1 = new TaskDTO.TaskBuilder()
                .name("test 1")
                .desc("test desc")
                .dueDate(new Date())
                .isDone(false)
                .addLabel(a)
                .build();

        t2 = new TaskDTO.TaskBuilder()
                .name("test 2")
                .desc("test 2 desc")
                .dueDate(new Date())
                .isDone(false)
                .addLabel(b)
                .build();

        t3 = new TaskDTO.TaskBuilder()
                .name("test 3")
                .desc("test 3 desc")
                .dueDate(new Date())
                .isDone(false)
                .addLabel(b)
                .addLabel(a)
                .build();

        tasks = Stream.of(t1, t2, t3)
                .collect(toList());

        tDate1 = new TaskDTO.TaskBuilder()
                    .dueDate(new Date(100L))
                    .build();

        tDate2 = new TaskDTO.TaskBuilder()
                .dueDate(new Date(1000L))
                .build();

        tName1 = new TaskDTO.TaskBuilder()
                .name("aaaa")
                .build();

        tName2 = new TaskDTO.TaskBuilder()
                .name("bbbbb")
                .build();
    }

    @Test
    public void testCompareList() {
        tasks.sort(Comparator.naturalOrder());
        List<TaskDTO> sorted = Stream.of(t3, t1, t2)
                                      .sorted()
                                      .collect(toList());
        assertEquals(tasks,sorted);
    }


    @Test
    public void testCompareByLabel() {

        assertTrue(t1.compareTo(t2) < 0);
        assertTrue(t3.compareTo(t2) < 0);
        assertTrue(t1.compareTo(t2) < 0);
    }

    @Test
    public void testCompareByDate() {
        assertTrue(tDate1.compareTo(tDate2) < 0);
    }

    @Test
    public void testCompareNames() {
        assertTrue(tName2.compareTo(tName1) > 0);
    }


}
