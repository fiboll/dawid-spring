package dawid.spring;

import dawid.spring.model.Task;
import dawid.spring.model.User;
import dawid.spring.provider.UserProvider;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.Date;
import java.util.Calendar;


/**
 * Created by dawid on 02.06.17.
 */

public class UserTest {

    @Mock
    private UserProvider userProvider;

    @Test
    public void createUserTest() {
        User user = buildSimpleUser();

        Assert.assertEquals("Jan", user.getFirstName());
        Assert.assertEquals("Kowalski", user.getSecondName());
    }

    @Test
    public void addTaskTest() {

        User user = buildSimpleUser();
        user.addTask(buildSimpleTask());

        Assert.assertNotNull(user.getUserTasks());
        Assert.assertEquals(1, user.getUserTasks().size());

    }

    private User buildSimpleUser() {
        return new User.UserBuilder()
                .id(1L)
                .firstName("Jan")
                .secondName("Kowalski")
                .build();
    }

    private Task buildSimpleTask() {
        return new Task.TaskBuilder()
                .id(1L)
                .desc("test desc")
                .name("test task")
                .dueDate(new Date(Calendar.getInstance().getTimeInMillis()))
                .build();
    }
}
