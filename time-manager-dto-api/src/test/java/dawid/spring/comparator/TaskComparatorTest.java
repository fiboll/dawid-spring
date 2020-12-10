package dawid.spring.comparator;

import dawid.spring.model.dto.LabelDTO;
import dawid.spring.model.dto.TaskDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
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
        a = LabelDTO.builder()
                .description("a")
                .colour("red")
                .build();

        b = LabelDTO.builder()
                .description("b")
                .colour("yellow")
                .build();

        t1 = TaskDTO.builder()
                .id(1L)
                .userName("test")
                .version(1L)
                .name("test 1")
                .desc("test desc")
                .dueDate(new Date())
                .isDone(false)
                .labels(Set.of(a))
                .build();

        t2 = TaskDTO.builder()
                .id(1L)
                .userName("test")
                .version(2L)
                .name("test 2")
                .desc("test 2 desc")
                .dueDate(new Date())
                .isDone(false)
                .labels(Set.of(b))
                .build();


        t3 = TaskDTO.builder()
                .id(1L)
                .userName("test")
                .version(3L)
                .name("test 3")
                .desc("test 3 desc")
                .dueDate(new Date())
                .isDone(false)
                .labels(Set.of(a, b))
                .build();

        tasks = Stream.of(t1, t2, t3)
                .collect(toList());

        tDate1 = TaskDTO.builder()
                    .dueDate(new Date(100L))
                    .labels(emptySet())
                    .build();

        tDate2 = TaskDTO.builder()
                .dueDate(new Date(1000L))
                .labels(emptySet())
                .build();

        tName1 = TaskDTO.builder()
                .name("aaaa")
                .labels(emptySet())
                .build();

        tName2 = TaskDTO.builder()
                .name("bbbbb")
                .labels(emptySet())
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
