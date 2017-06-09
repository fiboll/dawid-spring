package dawid.spring;

import dawid.spring.model.User;
import dawid.spring.provider.UserProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

/**
 * Created by dawid on 02.06.17.
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath:context.xml")
public class UserProviderMockTest {

    @Mock
    private UserProvider mockUserProvider;

    private Optional<User> exampleUser = Optional.empty();

    @Before
    public void init() {
        User user = new User.UserBuilder()
                .id(1L)
                .firstName("Jan")
                .secondName("Kowalski")
                .build();
        exampleUser = Optional.of(user);
    }

    @Test
    public void testGetUserByIdSimpleUser() {

        Mockito.when(mockUserProvider.getUserById(1L)).thenReturn(Optional.of(new User.UserBuilder().build()));

        Optional<User> user = mockUserProvider.getUserById(1L);

        Assert.assertTrue(user.isPresent());

    }

    @Test
    public void testGetUserByIdBuildUser() {

        Mockito.when(mockUserProvider.getUserById(1L)).thenReturn(exampleUser);

        Optional<User> user = mockUserProvider.getUserById(1L);

        Assert.assertTrue(user.isPresent());
        Assert.assertEquals("Jan", user.get().getFirstName());
        Assert.assertEquals("Kowalski", user.get().getSecondName());
    }

}
