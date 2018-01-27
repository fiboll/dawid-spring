package dawid.spring.comparator;

import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.entity.Label;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by private on 27.01.18.
 */
public class TaskComparatorTest {

    TaskDTO t1,t2,t3,t4;
    Label  a,b,c,d;

    @Before
    public void setup() {

        a = new Label("a", "red");
        b = new Label("b", "yellow");
        c = new Label("c", "green");
        d = new Label("d", "grey");



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
    }

    @Test
    public void testCompareList() {
        Stream.of(t1,t2,t3)
              .sorted()
              .collect(Collectors.toList())
              .forEach(System.out::println);
    }


    @Test
    public void testCompareByLabel() {

        System.out.println(t1.compareTo(t2));

//        Assert.assertTrue(t1.compareTo(t2) > 0);
//        Assert.assertTrue(t3.compareTo(t2) > 0);
//        Assert.assertTrue(t1.compareTo(t2) > 0);
    }

}
