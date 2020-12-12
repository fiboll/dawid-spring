package dawid.spring;

import dawid.spring.config.TableConfig;
import dawid.spring.exceptions.DomainException;
import dawid.spring.manager.*;
import dawid.spring.manager.impl.UserManagerImpl;
import dawid.spring.manager.impl.UserTable;
import dawid.spring.manager.impl.UserTableImpl;
import dawid.spring.model.dto.*;
import dawid.spring.model.Label;
import dawid.spring.model.Task;
import dawid.spring.model.User;
import dawid.spring.provider.LabelDao;
import dawid.spring.provider.UserDAO;
import dawid.spring.transformer.*;
import dawid.spring.transformer.impl.LabelTransformer;
import dawid.spring.transformer.impl.TaskTransformer;
import dawid.spring.transformer.impl.UserTransformer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.*;

import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by private on 23.12.17.
 */
@RunWith(value = Parameterized.class)
@Ignore
public class UserTableTest {

    @Parameterized.Parameters
    public static Collection tableImplementations() {
       return Set.of(new UserTableImpl(), new UserTable());
    }

    public UserTableTest(IUserTable userTable) {
        this.userTable = userTable;
    }

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
    private IUserTable userTable;
    @InjectMocks
    @Spy
    private ILabelTransformer labelTransformer = new LabelTransformer();

    @Before
    public void prepareUser() {
        initMocks(this);

        var user = new User();
        user.setEmail("tst@op.pl");
        user.setNickname("Dawid");
        user.setFirstName("Dawid");
        user.setSecondName("Dawidowicz");
        user.setPassword("qaz23");
        user.setVersion(1L);
        user.setId(1L);

        Label a = prepareLabel( "a");
        Label b = prepareLabel( "b");
        Label c = prepareLabel( "c");
        Label d = prepareLabel( "d");
        Label e = prepareLabel( "e");

        user.addTask(prepareTask("test", false, c,e));
        user.addTask(prepareTask("test1", false, a));
        user.addTask(prepareTask("test2", true, b));
        user.addTask(prepareTask("test3", true, e));
        user.addTask(prepareTask("test4", false));
        user.addTask(prepareTask("test5", false));
        user.addTask(prepareTask("test6", false));
        user.addTask(prepareTask("test7", false));

        when(userDAO.findByNick(anyString())).thenReturn(Optional.of(user));
        when(labelDao.getAllLabels()).thenReturn(asList(a, b, c, d, e));
    }

    @Test
    public void shouldReturnValidNumberOfDoneTasks() {
        var user = findUser();
        assertTrue(user.isPresent());
        assertEquals(2, userTable.getDoneTasks(user.get()).size());
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

    private static Label prepareLabel(String description) {
        var label = new Label();
        label.setDescription(description);
        return label;
    }

    private static Task prepareTask(String test, boolean isDone, Label... labels) {
        var task = new Task();
        task.setName(test);
        task.setDone(isDone);
        task.setDueDate(new Date());
        task.setLabels(new HashSet<>(Set.of(labels)));
        task.setDesc("desc");
        task.setId(1L);
        task.setVersion(1L);
        return task;
    }

    private Optional<UserDTO> findUser() {
        return userManager.findUserByNick("Dawid");
    }
}
