package dawid.spring;


import dawid.spring.model.Task;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;

public class TaskTest {

    @Test
    public void testTaskBuild() {

        long testDateLong = 10000000000l;

        Task task = new Task.TaskBuilder()
                .id(1L)
                .desc("test desc")
                .name("test task")
                .dueDate(new Date(testDateLong))
                .build();
        Assert.assertEquals(Long.valueOf("1"), task.getId());
        Assert.assertEquals("test desc", task.getDesc());
        Assert.assertEquals("test task", task.getName());

        Assert.assertEquals(new Date(testDateLong), task.getDueDate());
        task.getDueDate().setTime(5000000);
        Assert.assertEquals(new Date(testDateLong), task.getDueDate());


    }
}
