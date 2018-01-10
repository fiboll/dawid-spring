package dawid.spring;

import dawid.spring.config.TableConfig;
import dawid.spring.manager.IUserTable;
import dawid.spring.manager.UserManager;
import dawid.spring.manager.UserManagerImpl;
import dawid.spring.manager.UserTable;
import dawid.spring.model.Label;
import dawid.spring.model.Task;
import dawid.spring.model.User;
import dawid.spring.provider.UserDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

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

    @Spy
    TableConfig tableConfig = new TableConfig();

    @InjectMocks
    IUserTable userTable = new UserTable();

    @Before
    public void prepareUser() {

        MockitoAnnotations.initMocks(this);

        user = new User.UserBuilder().build();

        Task task = new Task.TaskBuilder().name("test").isDone(false).build();
        Task task1 = new Task.TaskBuilder().name("test1").isDone(false).build();
        Task task2 = new Task.TaskBuilder().name("test2").isDone(true).build();
        Task task3 = new Task.TaskBuilder().name("test3").isDone(true).build();
        Task task4 = new Task.TaskBuilder().name("test4").isDone(false).build();
        Task task5 = new Task.TaskBuilder().name("test5").isDone(false).build();
        Task task6 = new Task.TaskBuilder().name("test6").isDone(false).build();
        Task task7 = new Task.TaskBuilder().name("test7").isDone(false).build();

        user.addTask(task);
        user.addTask(task1);
        user.addTask(task2);
        user.addTask(task3);
        user.addTask(task4);
        user.addTask(task5);
        user.addTask(task6);
        user.addTask(task7);

        Label a = new Label();
        a.setDescription("a");

        Label b = new Label();
        b.setDescription("b");

        Label c = new Label();
        c.setDescription("c");

        Label d = new Label();
        d.setDescription("d");

        Label e = new Label();
        e.setDescription("e");

        task1.addLabel(a);
        task2.addLabel(b);
        task3.addLabel(e);
        task.addLabel(c);
        task.addLabel(e);

        Mockito.when(userDAO.findByNick(Mockito.anyString())).thenReturn(Optional.of(user));
    }

    @Test
    public void testIsDone() {
        Optional<User> user = userManager.findUserByNick("Dawid");
        Assert.assertTrue(user.isPresent());

        Assert.assertEquals(2, userTable.getDoneTasks(user.get()).size());
    }

    @Test
    public void testIsDoneAfterDone() {
        Optional<User> user = userManager.findUserByNick("Dawid");
        Assert.assertTrue(user.isPresent());

        Assert.assertEquals(2, userTable.getDoneTasks(user.get()).size());

        user.get().getTasks().stream()
            .filter(task -> !task.isDone())
            .findFirst()
            .ifPresent(Task::doneTask);

        Assert.assertEquals(3, userTable.getDoneTasks(user.get()).size());

    }

    @Test
    public void testIsDoing() {
        Optional<User> user = userManager.findUserByNick("Dawid");
        Assert.assertTrue(user.isPresent());

        Assert.assertEquals(1, userTable.getDoing(user.get()).size());
        Assert.assertEquals("test1", userTable.getDoing(user.get()).get(0).getName());
    }

    @Test
    public void testGetNextToDo() {
        Optional<User> user = userManager.findUserByNick("Dawid");
        Assert.assertTrue(user.isPresent());

        Assert.assertEquals(3, userTable.getNextToDo(user.get()).size());
        Assert.assertEquals("test", userTable.getNextToDo(user.get()).get(0).getName());
    }

    @Test
    public void testIsDoingAfterAddTask() {
        Optional<User> user = userManager.findUserByNick("Dawid");
        Assert.assertTrue(user.isPresent());

        Task task = new Task.TaskBuilder().name("addedTask").isDone(false).build();
        Label b = new Label();
        b.setDescription("a");
        task.addLabel(b);

        user.get().addTask(task);
        user.get().addTask(task);

        Assert.assertEquals(1, userTable.getDoing(user.get()).size());
        Assert.assertEquals("addedTask", userTable.getDoing(user.get()).get(0).getName());

    }

    @Test
    public void testGetNextToDoAfterAddTask() {
        Optional<User> user = userManager.findUserByNick("Dawid");
        Assert.assertTrue(user.isPresent());

        Task task = new Task.TaskBuilder().name("addedTask").isDone(false).build();
        Label b = new Label();
        b.setDescription("b");
        task.addLabel(b);

        user.get().addTask(task);

        Assert.assertEquals(3, userTable.getNextToDo(user.get()).size());
        Assert.assertEquals("addedTask", userTable.getNextToDo(user.get()).get(0).getName());
        Assert.assertEquals("test", userTable.getNextToDo(user.get()).get(1).getName());
    }
}
