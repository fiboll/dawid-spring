package dawid.spring.dao;

import dawid.spring.manager.UserManager;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:context_test.xml")
@Transactional
@Rollback
@Ignore
public class UserManagerTest {

    @Autowired
    private UserManager userManager;

    @Test
    public void testFindAll() {
        List<UserDTO> users =  userManager.getAllUsers();
        assertNotNull(users);
        assertEquals(2, users.size());

        users.forEach(
                (UserDTO u) -> assertNotNull(null != u.getId())
        );
    }

    @Test
    public void testUserByNick() {
        Optional<UserDTO> user = userManager.findUserByNick("fiboll");
        assertTrue(user.isPresent());
        assertEquals(user.get().getFirstName(), "Dawid");
        assertEquals(user.get().getSecondName(), "Strembicki");
        assertEquals(user.get().getNickname(), "fiboll");
    }

    @Test
    public void testAddTaskToUSer() {
        Optional<UserDTO> foundedUser = userManager.findUserByNick("fiboll");
        assertNotNull(foundedUser);
        TaskDTO task = TaskDTO.builder().name("Test Task")
                                       .id(1L)
                                       .dueDate(Date.from(LocalDateTime.now().plus(2, ChronoUnit.MONTHS).atZone(ZoneId.systemDefault()).toInstant()))
                                       .desc("Test desc")
                                       .isDone(false)
                                       .version(1L)
                                       .userName("fiboll")
                                       .build();

        userManager.addTaskToUSer(foundedUser.get(), task);

        foundedUser = userManager.findUserByNick("fiboll");
        assertNotNull(foundedUser);
    }
}
