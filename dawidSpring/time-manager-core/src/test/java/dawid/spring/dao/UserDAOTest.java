package dawid.spring.dao;

import dawid.spring.model.entity.Task;
import dawid.spring.model.entity.User;
import dawid.spring.provider.UserDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Optional;

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
        userDAO.removeUser(findKnownUserWithId(1L));
        userDAO.removeUser(findKnownUserWithId(2L));
        assertTrue(userDAO.findAll().isEmpty());
    }

    @Test
    public void testAddTask() {
        User user = userDAO.findByNick("fiboll").
                orElseThrow(() -> new IllegalStateException("User do not exist"));

        Task task = new Task();
        task.setDesc("test desc a");
        task.setName("test task b");
        task.setDueDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));

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
