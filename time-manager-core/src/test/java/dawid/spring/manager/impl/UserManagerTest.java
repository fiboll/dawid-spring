package dawid.spring.dao;

import dawid.spring.manager.UserManager;
import dawid.spring.manager.impl.UserManagerImpl;
import dawid.spring.model.User;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;
import dawid.spring.provider.UserDAO;
import dawid.spring.transformer.impl.UserTransformer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class UserManagerTest {

    @InjectMocks
    private UserManager userManager = new UserManagerImpl();

    @Mock
    private UserDAO userDAO;

    @Mock
    private UserTransformer userTransformer;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        when(userDAO.findAll()).thenReturn(List.of(new User(), new User()));
        when(userTransformer.entityToDTO(any())).thenReturn(UserDTO.builder().build());


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
