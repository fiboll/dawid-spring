package dawid.spring;

import dawid.spring.model.Task;
import dawid.spring.model.User;
import dawid.spring.provider.UserManager;
import dawid.spring.provider.UserProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * Created by dawid on 02.06.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context_test.xml")
@Transactional
@Rollback
public class UserProviderTest {

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private UserManager userManager;

    @Test
    public void testGetUserById() {
        User user = new User.UserBuilder()
                .firstName("Dawid")
                .secondName("Strembicki")
                .build();

        userProvider.addUser(user);
        Optional<User> findedUser = userProvider.getUserById(user.getId());

        Assert.assertTrue(findedUser.isPresent());

        user = findedUser.get();

        Assert.assertEquals("Dawid", user.getFirstName());
        Assert.assertEquals("Strembicki", user.getSecondName());
    }

    @Test
    public void testAddUser() {
        User user = new User.UserBuilder()
                .firstName("Jan")
                .secondName("Kowalski")
                .build();
        Assert.assertNotNull(user);

        userProvider.addUser(user);
        System.out.println(user);
    }

    @Test
    public void testDeleteUser() {
        User user = new User.UserBuilder()
                .firstName("Jan")
                .secondName("Kowalski")
                .build();
        Assert.assertNotNull(user);

        userProvider.addUser(user);
        userProvider.removeUser(user);
    }

    @Test
    public void testAddTask() {
        User user = userProvider.findByNameAndSurname("Dawid", "Strembicki").
                orElseThrow(() -> new IllegalStateException("User do not exist"));

        Task task = new Task.TaskBuilder()
                .desc("test desc a")
                .name("test task b")
                .dueDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()))
                .build();

        user.addTask(task);

        //TODO fix me
        //user = userProvider.update(user);
    }

    @Test
    public void testFindAll() {
        List<User> users =  userManager.getAllUsers();
        Assert.assertNotNull(users);
        Assert.assertEquals(8, users.size());

        users.forEach(
               (User u) -> Assert.assertTrue(null != u.getId())
        );
    }

}
