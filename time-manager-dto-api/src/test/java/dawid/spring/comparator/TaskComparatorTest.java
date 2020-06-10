package dawid.spring.comparator;

import dawid.spring.model.dto.ImmutableLabelDTO;
import dawid.spring.model.dto.ImmutableTaskDTO;
import dawid.spring.model.dto.LabelDTO;
import dawid.spring.model.dto.TaskDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by private on 27.01.18.
 */
public class TaskComparatorTest {

    private TaskDTO t1,t2,t3, tDate1, tDate2, tName1, tName2;
    private LabelDTO a,b;

    private List<TaskDTO> tasks;

    @BeforeEach
    public void setup() {
        a = ImmutableLabelDTO.builder()
                .description("a")
                .colour("red")
                .build();

        b = ImmutableLabelDTO.builder()
                .description("b")
                .colour("yellow")
                .build();

        t1 = ImmutableTaskDTO.builder()
                .id(1L)
                .userName("test")
                .version(1L)
                .name("test 1")
                .desc("test desc")
                .dueDate(new Date())
                .done(false)
                .addLabels(a)
                .build();

        t2 = ImmutableTaskDTO.builder()
                .id(1L)
                .userName("test")
                .version(2L)
                .name("test 2")
                .desc("test 2 desc")
                .dueDate(new Date())
                .done(false)
                .addLabels(b)
                .build();


        t3 = ImmutableTaskDTO.builder()
                .id(1L)
                .userName("test")
                .version(3L)
                .name("test 3")
                .desc("test 3 desc")
                .dueDate(new Date())
                .done(false)
                .addLabels(a, b)
                .build();

        tasks = Stream.of(t1, t2, t3)
                .collect(toList());

//        tDate1 = new TaskDTO.TaskBuilder()
//                    .dueDate(new Date(100L))
//                    .build();
//
//        tDate2 = new TaskDTO.TaskBuilder()
//                .dueDate(new Date(1000L))
//                .build();
//
//        tName1 = new TaskDTO.TaskBuilder()
//                .name("aaaa")
//                .build();
//
//        tName2 = new TaskDTO.TaskBuilder()
//                .name("bbbbb")
//                .build();
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
