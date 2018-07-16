package dawid.spring;

import dawid.spring.config.TableConfig;
import dawid.spring.manager.IUserTable;
import dawid.spring.manager.UserManager;
import dawid.spring.manager.UserManagerImpl;
import dawid.spring.manager.UserTable;
import dawid.spring.model.dto.LabelDTO;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;
import dawid.spring.model.entity.Label;
import dawid.spring.model.entity.Task;
import dawid.spring.model.entity.User;
import dawid.spring.provider.LabelDao;
import dawid.spring.provider.UserDAO;
import dawid.spring.transformer.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;

/**
 * Created by private on 23.12.17.
 */
public class UserTableTest {

    private User user;

    @Mock
    UserDAO userDAO;

    @Mock
    LabelDao labelDao;

    @InjectMocks
    @Spy
    IUserTransformer userTransformer = new UserTransformer();

    @InjectMocks
    @Spy
    ITaskTransformer taskTransformer = new TaskTransformer();

    @InjectMocks
    @Spy
    UserManager userManager = new UserManagerImpl();

    @Spy
    TableConfig tableConfig = new TableConfig();

    @InjectMocks
    @Spy
    IUserTable userTable = new UserTable();

    @InjectMocks
    @Spy
    ILabelTransformer labelTransformer = new LabelTransformer();

    @Before
    public void prepareUser() {
        MockitoAnnotations.initMocks(this);
        user = new User();

        Task task = prepareTask("test", false);
        Task task1 = prepareTask("test1", false);
        Task task2 = prepareTask("test2", true);
        Task task3 = prepareTask("test3", true);
        Task task4 = prepareTask("test4", false);
        Task task5 = prepareTask("test5", false);
        Task task6 = prepareTask("test6", false);
        Task task7 = prepareTask("test7", false);

        Label a = prepareLabel(1L, "a");
        Label b = prepareLabel(2L, "b");
        Label c = prepareLabel(3L, "c");
        Label d = prepareLabel(4L, "d");
        Label e = prepareLabel(5L, "e");

        task.addLabel(c);
        task.addLabel(e);
        task1.addLabel(a);
        task2.addLabel(b);
        task3.addLabel(e);

        user.addTask(task);
        user.addTask(task1);
        user.addTask(task2);
        user.addTask(task3);
        user.addTask(task4);
        user.addTask(task5);
        user.addTask(task6);
        user.addTask(task7);

        Mockito.when(userDAO.findByNick(anyString())).thenReturn(Optional.ofNullable(user));
        Mockito.when(labelDao.getAllLabels()).thenReturn(Arrays.asList(a, b, c, d, e));
    }

    @Test
    public void testIsDone() {
        Optional<UserDTO> user = findUser();
        assertTrue(user.isPresent());
        assertEquals(2, userTable.getDoneTasks(user.get()).size());
    }

    @Test
    public void testIsDoneAfterDoneTask() {
        Optional<UserDTO> user = findUser();
        assertTrue(user.isPresent());
        assertEquals(2, userTable.getDoneTasks(user.get()).size());

        doneOneTask(user.get());

        assertEquals(3, userTable.getDoneTasks(user.get()).size());
    }

    private void doneOneTask(UserDTO user) {
        user.getTasks().stream()
                .filter(task -> task.isDone() == false)
                .findAny()
                .ifPresent(TaskDTO::doneTask);
    }

    @Test
    public void testIsDoing() {
        Optional<UserDTO> user = findUser();
        assertTrue(user.isPresent());

        assertEquals(1, userTable.getDoing(user.get()).size());
        assertEquals("test1", userTable.getDoing(user.get()).get(0).getName());
    }

    @Test
    public void testGetNextToDo() {
        Optional<UserDTO> user = findUser();
        assertTrue(user.isPresent());

        assertEquals(3, userTable.getNextToDo(user.get()).size());
        assertEquals("test", userTable.getNextToDo(user.get()).get(0).getName());
    }

    @Test
    public void testIsDoingAfterAddTask() {
        Optional<UserDTO> user = findUser();

        assertTrue(user.isPresent());

        TaskDTO taskDTO = new TaskDTO.TaskBuilder()
                .name("addedTask")
                .isDone(false)
                .addLabel(new LabelDTO(1L, "a", ""))
                .build();

        user.get().addTask(taskDTO);

        assertEquals(1, userTable.getDoing(user.get()).size());
        assertEquals("addedTask", userTable.getDoing(user.get()).get(0).getName());
    }

    @Test
    public void testGetNextToDoAfterAddTask() {
        Optional<UserDTO> user = findUser();
        assertTrue(user.isPresent());

        TaskDTO addedTask = new TaskDTO.TaskBuilder()
                .name("addedTask")
                .isDone(false)
                .addLabel(new LabelDTO(2L, "b", ""))
                .build();

        user.get().addTask(addedTask);
        assertEquals(3, userTable.getNextToDo(user.get()).size());
        assertEquals("addedTask", userTable.getNextToDo(user.get()).get(0).getName());
    }

    @Test
    public void testGetBacklogTask() {
        Optional<UserDTO> user = findUser();
        assertTrue(user.isPresent());
        assertEquals(2, userTable.getBacklogTask(user.get()).size());
    }

    private static Label prepareLabel(Long id, String description) {
        Label label = new Label();
        label.setId(id);
        label.setDescription(description);
        return label;
    }

    private static Task prepareTask(String test, boolean isDone) {
        Task task = new Task();
        task.setName(test);
        task.setDone(isDone);
        return task;
    }

    private Optional<UserDTO> findUser() {
        return userManager.findUserByNick("Dawid");
    }
}
