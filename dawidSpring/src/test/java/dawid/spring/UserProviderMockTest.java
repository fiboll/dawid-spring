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

/**
 * Created by dawid on 02.06.17.
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath:context.xml")
public class UserProviderMockTest {

    @Mock
    private UserProvider mockUserProvider;

    private User exampleUser = null;

    @Before
    public void init() {
        exampleUser = new User.UserBuilder()
                .id(1L)
                .firstName("Jan")
                .secondName("Kowalski")
                .build();
    }

    @Test
    public void testGetUserByIdSimpleUser() {

        Mockito.when(mockUserProvider.getUserById(1l)).thenReturn(new User.UserBuilder().build());

        //
        Long id = 1l;
        User user = mockUserProvider.getUserById(id);

        Assert.assertNotNull(user);

    }

    @Test
    public void testGetUserByIdBuildUser() {

        Mockito.when(mockUserProvider.getUserById(1l)).thenReturn(exampleUser);

        //
        Long id = 1l;
        User user = mockUserProvider.getUserById(id);

        Assert.assertNotNull(user);
        Assert.assertEquals("Jan", user.getFirstName());
        Assert.assertEquals("Kowalski", user.getSecondName());
    }

}
