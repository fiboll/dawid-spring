package dawid.spring;

import dawid.spring.model.User;
import dawid.spring.provider.UserProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by dawid on 02.06.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context.xml")
public class UserProviderTest {


    @Autowired
    private UserProvider userProvider;

    @Autowired
    ApplicationContext context;

    @Test
    @Rollback
    public void testGetUserById() {
        User user = new User.UserBuilder()
                .firstName("Dawid")
                .secondName("Strembicki")
                .build();

        userProvider.addUser(user);
        user = userProvider.getUserById(user.getId());


        Assert.assertEquals("Dawid", user.getFirstName());
        Assert.assertEquals("Strembicki", user.getSecondName());
    }

    @Test
    @Rollback
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
    @Rollback
    public void testDeleteUser() {
        User user = new User.UserBuilder()
                .firstName("Jan")
                .secondName("Kowalski")
                .build();
        Assert.assertNotNull(user);

        userProvider.addUser(user);

        userProvider.removeUser(user);

    }


}
