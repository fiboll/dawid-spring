package dawid.spring.manager.impl;

import dawid.spring.manager.UserManager;
import dawid.spring.model.User;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;
import dawid.spring.provider.UserDAO;
import dawid.spring.transformer.impl.UserTransformer;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        User user = new User();
        UserDTO userDTO = UserDTO.builder()
                .id(RandomUtils.nextLong())
                .nickname("fiboll")
                .build();
        when(userDAO.findByNick("fiboll")).thenReturn(Optional.of(user));
        when(userTransformer.entityToDTO(user)).thenReturn(userDTO);

        Optional<UserDTO> result = userManager.findUserByNick("fiboll");
        assertEquals(Optional.of(userDTO), result);
    }

    @Test
    public void testAddTaskToUSer() {
        User user = new User();
        user.setTasks(new HashSet<>());
        UserDTO userDTO = UserDTO.builder().
                id(RandomUtils.nextLong())
                .tasks(new HashSet<>())
                .nickname("fiboll")
                .build();

        when(userDAO.findByNick("fiboll")).thenReturn(Optional.of(user));
        when(userTransformer.entityToDTO(user)).thenReturn(userDTO);

        TaskDTO task = TaskDTO.builder().name("Test Task")
                                       .id(1L)
                                       .dueDate(Date.from(LocalDateTime.now().plus(2, ChronoUnit.MONTHS).atZone(ZoneId.systemDefault()).toInstant()))
                                       .desc("Test desc")
                                       .isDone(false)
                                       .version(1L)
                                       .userName("fiboll")
                                       .build();

        userManager.addTaskToUSer(userDTO, task);

        verify(userTransformer, times(1)).update(user, userDTO);
    }
}
