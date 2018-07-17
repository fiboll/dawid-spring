package dawid.spring.dao;

import dawid.spring.manager.UserManager;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;
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

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context_test.xml")
@Transactional
@Rollback
public class UserManagerTest {

    @Autowired
    private UserManager userManager;

    @Test
    public void testFindAll() {
        List<UserDTO> users =  userManager.getAllUsers();
        assertNotNull(users);
        Assert.assertEquals(2, users.size());

        users.forEach(
                (UserDTO u) -> assertNotNull(null != u.getId())
        );
    }

    @Test
    public void testUserByNick() {
        Optional<UserDTO> user = userManager.findUserByNick("fiboll");
        assertNotNull(user);
        Assert.assertEquals(user.get().getFirstName(), "Dawid");
        Assert.assertEquals(user.get().getSecondName(), "Strembicki");
        Assert.assertEquals(user.get().getNickname(), "fiboll");
    }

    @Test
    public void testAddTaskToUSer() {
        Optional<UserDTO> foundedUser = userManager.findUserByNick("fiboll");
        assertNotNull(foundedUser);

        Calendar dueDate = Calendar.getInstance();
        dueDate.add(Calendar.MONTH, 2);
        TaskDTO task = new TaskDTO.TaskBuilder().name("Test Task")
                                             .dueDate(dueDate.getTime())
                                             .desc("Test desc")
                                             .build();

        userManager.addTaskToUSer(foundedUser.get(), task);

        foundedUser = userManager.findUserByNick("fiboll");
        assertNotNull(foundedUser);

    }

}
