package dawid.spring;

import dawid.spring.manager.UserManager;
import dawid.spring.manager.UserManagerImpl;
import dawid.spring.model.IUserTable;
import dawid.spring.model.Task;
import dawid.spring.model.User;
import dawid.spring.model.UserTable;
import dawid.spring.provider.UserDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

/**
 * Created by private on 23.12.17.
 */
public class UserTableTest {

    User user;

    @Mock
    UserDAO userDAO;

    @InjectMocks
    UserManager userManager = new UserManagerImpl();

    IUserTable userTable = new UserTable();

    @Before
    public void prepareUser() {

        MockitoAnnotations.initMocks(this);

        user = new User.UserBuilder().build();
        user.addTask(new Task.TaskBuilder().name("test1").isDone(false).build());
        user.addTask(new Task.TaskBuilder().name("test2").isDone(false).build());
        user.addTask(new Task.TaskBuilder().name("test3").isDone(true).build());
        user.addTask(new Task.TaskBuilder().name("test4").isDone(true).build());
        Mockito.when(userDAO.findByNick(Mockito.anyString())).thenReturn(Optional.of(user));
    }

    @Test
    public void testIsDone() {
        Optional<User> user = userManager.findUserByNick("Dawid");
        Assert.assertTrue(user.isPresent());

        Assert.assertEquals(2, userTable.getDoneTasks(user.get()).size());
    }
}
