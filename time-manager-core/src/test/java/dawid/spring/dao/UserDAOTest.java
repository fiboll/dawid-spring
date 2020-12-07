package dawid.spring.dao;

import dawid.spring.model.Task;
import dawid.spring.model.User;
import dawid.spring.provider.TaskDao;
import dawid.spring.provider.UserDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by dawid on 02.06.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context_test.xml")
@Transactional
@Rollback
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TaskDao taskDao;

    @Test
    public void testFindUserByNick() {
        Optional<User> foundUser = userDAO.findByNick("fiboll");
        assertTrue(foundUser.isPresent());

        User user = foundUser.get();

        assertEquals("Dawid", user.getFirstName());
        assertEquals("Strembicki", user.getSecondName());
        assertEquals("fiboll@o2.pl", user.getEmail());
        assertEquals("qaz12345", user.getPassword());
        assertEquals("fiboll", user.getNickname());
    }

    @Test
    public void testAddUser() {
        User user = new User();
        user.setFirstName("Jan");
        user.setSecondName("Kowalski");
        user.setNickname("ktoś");
        user.setPassword("password");
        user.setEmail("email@o2.pl");
        userDAO.addUser(user);

        assertEquals(3, userDAO.findAll().size());
    }

    @Test
    public void testDeleteUser() {
        List<Long> tasks = findKnownUserWithId(1L).getTasks().stream().map(Task::getId).collect(Collectors.toList());
        userDAO.removeUser(findKnownUserWithId(1L));
        for (Long taskId: tasks) {
            assertEquals(null,taskDao.getTaskById(taskId));
        }

        tasks = findKnownUserWithId(2L).getTasks().stream().map(Task::getId).collect(Collectors.toList());
        userDAO.removeUser(findKnownUserWithId(2L));

        for (Long taskId: tasks) {
            assertEquals(null,taskDao.getTaskById(taskId));
        }
        assertTrue(userDAO.findAll().isEmpty());
    }

    @Test
    public void testAddTask() {
        User user = userDAO.findByNick("fiboll").
                orElseThrow(() -> new IllegalStateException("User do not exist"));

        Task task = new Task();
        task.setDesc("test desc a");
        task.setName("test task b");
        task.setDueDate(new Date(Calendar.getInstance().getTimeInMillis()));

        user.addTask(task);
        user = userDAO.update(user);
        assertNotNull(user.getId());
        assertEquals(8, user.getTasks().size());
    }

    private User findKnownUserWithId(long id) {
        return userDAO.getUserById(id).orElseThrow(() -> new IllegalStateException("can find user"));
    }

    private User findKnownUserWithNick(String nick) {
        return userDAO.findByNick(nick).orElseThrow(() -> new IllegalStateException("can find user"));
    }
}
