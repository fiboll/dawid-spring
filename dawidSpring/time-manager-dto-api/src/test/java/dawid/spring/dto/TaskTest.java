package dawid.spring.dto;


import dawid.spring.model.dto.TaskDTO;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.assertEquals;

public class TaskTest {

    @Test
    public void testTaskBuild() {

        long testDateLong = 10000000000l;

        TaskDTO task = new TaskDTO.TaskBuilder()
                .id(1L)
                .desc("test desc")
                .name("test task")
                .dueDate(new Date(testDateLong))
                .build();

        assertEquals(Long.valueOf("1"), task.getId());
        assertEquals("test desc", task.getDesc());
        assertEquals("test task", task.getName());

        assertEquals(new Date(testDateLong), task.getDueDate());
        task.getDueDate().setTime(5000000);
        assertEquals(new Date(testDateLong), task.getDueDate());

    }
}
