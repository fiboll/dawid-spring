package dawid.spring.dao;

import dawid.spring.model.entity.Task;
import dawid.spring.model.entity.User;
import dawid.spring.provider.UserDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Optional;

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
    public void testGetUserById() {
        User user = new User.UserBuilder()
                .nickname("fiboll")
                .firstName("Dawid")
                .secondName("Strembicki")
                .build();

        userDAO.addUser(user);
        Optional<User> findedUser = userDAO.getUserById(user.getId());

        Assert.assertTrue(findedUser.isPresent());

        user = findedUser.get();

        Assert.assertEquals("Dawid", user.getFirstName());
        Assert.assertEquals("Strembicki", user.getSecondName());
        Assert.assertEquals("fiboll", user.getNickname());
    }

    @Test
    public void testAddUser() {
        User user = new User.UserBuilder()
                .firstName("Jan")
                .secondName("Kowalski")
                .nickname("ktoś")
                .build();
        Assert.assertNotNull(user);
        System.out.println(user);
    }

    @Test
    public void testDeleteUser() {
        User user = new User.UserBuilder()
                .firstName("Jan")
                .secondName("Kowalski")
                .nickname("ktoś")
                .build();
        Assert.assertNotNull(user);

        userDAO.addUser(user);
        userDAO.removeUser(user);
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
    }

}
