package dawid.spring;


import dawid.spring.model.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

public class TaskTest {

    @Test
    public void testTaskBuild() {
        Task task = new Task.TaskBuider()
                .id(1L)
                .desc("test desc")
                .name("test task")
                .dueDate(new Date(10000000000l))
                .build();
        Assert.assertEquals(Long.valueOf("1"), task.getId());
        Assert.assertEquals("test desc", task.getDesc());
        Assert.assertEquals("test task", task.getName());
    }
}
