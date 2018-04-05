package dawid.spring;

import dawid.spring.config.TableConfig;
import dawid.spring.manager.IUserTable;
import dawid.spring.manager.UserManager;
import dawid.spring.manager.UserManagerImpl;
import dawid.spring.manager.UserTable;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;
import dawid.spring.model.entity.Label;
import dawid.spring.model.entity.Task;
import dawid.spring.model.entity.User;
import dawid.spring.provider.UserDAO;
import dawid.spring.transformer.ITaskTransformer;
import dawid.spring.transformer.IUserTransformer;
import dawid.spring.transformer.TaskTransformer;
import dawid.spring.transformer.UserTransformer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.Optional;

/**
 * Created by private on 23.12.17.
 */
public class UserTableTest {

    private User user;

    @Mock
    UserDAO userDAO;

    @InjectMocks
    @Spy
    IUserTransformer userTransformer = new UserTransformer();

    @Spy
    ITaskTransformer taskTransformer = new TaskTransformer();

    @InjectMocks
    UserManager userManager = new UserManagerImpl();

    @Spy
    TableConfig tableConfig = new TableConfig();

    @InjectMocks
    IUserTable userTable = new UserTable();

    @Before
    public void prepareUser() {

        MockitoAnnotations.initMocks(this);

        user = new User();

        Task task =  new Task();
        task.setName("test");
        task.setDone(false);

        Task task1 = new Task();
        task1.setName("test1");
        task1.setDone(false);

        Task task2 = new Task();
        task2.setName("test2");
        task2.setDone(true);

        Task task3 = new Task();
        task3.setName("test3");
        task3.setDone(true);

        Task task4 = new Task();
        task4.setName("test4");
        task4.setDone(false);

        Task task5 = new Task();
        task5.setName("test5");
        task5.setDone(false);

        Task task6 = new Task();
        task6.setName("test6");
        task6.setDone(false);

        Task task7 = new Task();
        task7.setName("test7");
        task7.setDone(false);


        Label a = new Label("a");
        Label b = new Label("b");
        Label c = new Label("c");
        Label d = new Label("d");
        Label e = new Label("e");

        task1.addLabel(a);
        task2.addLabel(b);
        task3.addLabel(e);
        task.addLabel(c);
        task.addLabel(e);


        user.addTask(task);
        user.addTask(task1);
        user.addTask(task2);
        user.addTask(task3);
        user.addTask(task4);
        user.addTask(task5);
        user.addTask(task6);
        user.addTask(task7);

        Mockito.when(userDAO.findByNick(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
    }

    @Test
    public void testIsDone() {
        Optional<UserDTO> user = userManager.findUserByNick("Dawid");
        Assert.assertTrue(user.isPresent());

        Assert.assertEquals(2, userTable.getDoneTasks(user.get()).size());
    }

    @Test
    public void testIsDoneAfterDone() {
        Optional<UserDTO> user = userManager.findUserByNick("Dawid");
        Assert.assertTrue(user.isPresent());

        Assert.assertEquals(2, userTable.getDoneTasks(user.get()).size());

        user.get().getTasks().stream()
            .filter(task -> !task.isDone())
            .findFirst()
            .ifPresent(TaskDTO::doneTask);

        Assert.assertEquals(3, userTable.getDoneTasks(user.get()).size());

    }

    @Test
    public void testIsDoing() {
        Optional<UserDTO> user = userManager.findUserByNick("Dawid");
        Assert.assertTrue(user.isPresent());

        Assert.assertEquals(1, userTable.getDoing(user.get()).size());
        Assert.assertEquals("test1", userTable.getDoing(user.get()).get(0).getName());
    }

    @Test
    public void testGetNextToDo() {
        Optional<UserDTO> user = userManager.findUserByNick("Dawid");
        Assert.assertTrue(user.isPresent());

        Assert.assertEquals(3, userTable.getNextToDo(user.get()).size());
        Assert.assertEquals("test", userTable.getNextToDo(user.get()).get(0).getName());
    }

    @Test
    public void testIsDoingAfterAddTask() {
        Optional<UserDTO> user = userManager.findUserByNick("Dawid");
        Assert.assertTrue(user.isPresent());

        TaskDTO.TaskBuilder taskBuilder = new TaskDTO.TaskBuilder().name("addedTask").isDone(false);
        Label b = new Label("a");
        b.setDescription("a");

        TaskDTO taskDTO = taskBuilder.build();

        user.get().addTask(taskDTO);
        user.get().addTask(taskDTO);

        Assert.assertEquals(1, userTable.getDoing(user.get()).size());
        Assert.assertEquals("addedTask", userTable.getDoing(user.get()).get(0).getName());

    }

    @Test
    public void testGetNextToDoAfterAddTask() {
        Optional<UserDTO> user = userManager.findUserByNick("Dawid");
        Assert.assertTrue(user.isPresent());

        TaskDTO.TaskBuilder taskBuilder = new TaskDTO.TaskBuilder().name("addedTask").isDone(false);
        Label b = new Label("b");
        taskBuilder.addLabel(b);

        TaskDTO buildTask = taskBuilder.build();
        user.get().addTask(buildTask);

        Assert.assertEquals(3, userTable.getNextToDo(user.get()).size());
        Assert.assertEquals("addedTask", userTable.getNextToDo(user.get()).get(0).getName());

    }

    @Test
    public void testGetBacklogTask() {
        Optional<UserDTO> user = userManager.findUserByNick("Dawid");
        Assert.assertTrue(user.isPresent());

        Assert.assertEquals(2, userTable.getBacklogTask(user.get()).size());
    }

}
