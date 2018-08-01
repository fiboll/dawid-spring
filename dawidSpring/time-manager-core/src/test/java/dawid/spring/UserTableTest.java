package dawid.spring;

import dawid.spring.config.TableConfig;
import dawid.spring.exceptions.DomainException;
import dawid.spring.manager.IUserTable;
import dawid.spring.manager.UserManager;
import dawid.spring.manager.UserManagerImpl;
import dawid.spring.manager.UserTableImpl;
import dawid.spring.model.dto.*;
import dawid.spring.model.entity.Label;
import dawid.spring.model.entity.Task;
import dawid.spring.model.entity.User;
import dawid.spring.provider.LabelDao;
import dawid.spring.provider.UserDAO;
import dawid.spring.transformer.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;

/**
 * Created by private on 23.12.17.
 */
public class UserTableTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private LabelDao labelDao;

    @InjectMocks
    @Spy
    private IUserTransformer userTransformer = new UserTransformer();

    @InjectMocks
    @Spy
    private ITaskTransformer taskTransformer = new TaskTransformer();

    @InjectMocks
    @Spy
    private UserManager userManager = new UserManagerImpl();

    @Spy
    private TableConfig tableConfig = new TableConfig();

    @InjectMocks
    @Spy
    private IUserTable userTable = new UserTableImpl();

    @InjectMocks
    @Spy
    private ILabelTransformer labelTransformer = new LabelTransformer();

    @Before
    public void prepareUser() {
        MockitoAnnotations.initMocks(this);
        var user = new User();
        user.setEmail("tst@op.pl");
        user.setNickname("Dawid");
        user.setFirstName("Dawid");
        user.setSecondName("Dawidowicz");
        user.setPassword("qaz23");
        user.setVersion(1L);
        user.setId(1L);

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

        Mockito.when(userDAO.findByNick(anyString())).thenReturn(Optional.of(user));
        Mockito.when(labelDao.getAllLabels()).thenReturn(Arrays.asList(a, b, c, d, e));
    }

    @Test
    public void shouldReturnValidNumberOfDoneTasks() {
        var user = findUser();
        assertTrue(user.isPresent());
        assertEquals(2, userTable.getDoneTasks(user.get()).size());
    }

    @Test
    public void shouldReturnValidNumberOfDoneTaskAfterDoneOneTasks() {
        var user = findUser();
        assertTrue(user.isPresent());
        assertEquals(2, userTable.getDoneTasks(user.get()).size());

        doneOneTask(user.get());

        assertEquals(3, userTable.getDoneTasks(user.get()).size());
    }

    private void doneOneTask(UserDTO user) {
        user.getTasks().stream()
                .filter(task -> !task.isDone())
                .findAny()
                .ifPresent(TaskDTO::doneTask);
    }

    @Test
    public void shouldReturnValidNumberOfDoingTasks() {
        var user = findUser();
        assertTrue(user.isPresent());

        assertEquals(1, userTable.getDoingTasks(user.get()).size());
        assertEquals("test1", userTable.getDoingTasks(user.get()).get(0).getName());
    }

    @Test
    public void shouldReturnValidNumberOfNextToDoTasks() {
        var user = findUser();
        assertTrue(user.isPresent());
        assertEquals(3, userTable.getNextToDoTasks(user.get()).size());
    }

    @Test
    public void shouldReturnValidNextToDoTask() {
        var user = findUser();
        assertTrue(user.isPresent());
        assertEquals("test", userTable.getNextToDoTasks(user.get()).get(0).getName());
    }

    @Test
    public void shouldReturnValidIsDoingAfterAddTask() {
        var user = findUser();

        assertTrue(user.isPresent());

        var taskDTO = ImmutableTaskDTO.builder()
                                      .name("addedTask")
                                      .isDone(false)
                                      //.addLabel(new LabelDTO(1L, "a", ""))
                                      .build();
        var updatedUser = ImmutableUserDTO.builder()
                                          .from(user.get())
                                          .addTasks(taskDTO)
                                          .build();

        assertEquals(1, userTable.getDoingTasks(updatedUser).size());
        assertEquals("addedTask", userTable.getDoingTasks(updatedUser).get(0).getName());
    }

    @Test
    public void shouldReturnValidGetNextToDoAfterAddTask() {
        var user = findUser();
        assertTrue(user.isPresent());

        var addedTask = ImmutableTaskDTO.builder()
                                        .name("addedTask")
                                        .isDone(false)
                                        .addLabels(ImmutableLabelDTO.builder()
                                                                    .id(2L)
                                                                    .description("b")
                                                                    .build())
                                        .build();

        final ImmutableUserDTO addedUser = ImmutableUserDTO.builder()
                                                           .from(user.get())
                                                           .addTasks(addedTask)
                                                           .build();
        assertEquals(3, userTable.getNextToDoTasks(addedUser).size());
        assertEquals("addedTask", userTable.getNextToDoTasks(addedUser).get(0).getName());
    }

    @Test
    public void shouldReturnValidNumberOfGetBacklogTask() {
        var user = findUser();
        assertTrue(user.isPresent());
        assertEquals(2, userTable.getBacklogTasks(user.get()).size());
    }

    @Test(expected = DomainException.class)
    public void shouldThrowDomainExceptionForGetBacklogTasksWithNullUser() {
        userTable.getBacklogTasks(null);
    }

    @Test(expected = DomainException.class)
    public void shouldThrowDomainExceptionForGetNextToDosWithNullUser() {
        userTable.getNextToDoTasks(null);
    }

    @Test(expected = DomainException.class)
    public void shouldThrowDomainExceptionForGetDoingWithNullUser() {
        userTable.getDoingTasks(null);
    }

    @Test(expected = DomainException.class)
    public void shouldThrowDomainExceptionForGetDoneTasksWithNullUser() {
        userTable.getDoneTasks(null);
    }

    private static Label prepareLabel(Long id, String description) {
        var label = new Label();
        label.setId(id);
        label.setDescription(description);
        return label;
    }

    private static Task prepareTask(String test, boolean isDone) {
        var task = new Task();
        task.setName(test);
        task.setDone(isDone);
        task.setDueDate(new Date());
        task.setLabels(new HashSet<>(Set.of(prepareLabel(1L, "a"))));
        task.setDesc("desc");
        task.setId(1L);
        task.setVersion(1L);
        return task;
    }

    private Optional<UserDTO> findUser() {
        return userManager.findUserByNick("Dawid");
    }
}
