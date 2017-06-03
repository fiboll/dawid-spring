package dawid.spring;

import dawid.spring.model.User;
import dawid.spring.provider.UserProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
    public void testGetUserById() {
        User user = userProvider.getUserById(1L);
        Assert.assertNotNull(user);

        Assert.assertEquals("Dawid", user.getFirstName());
        Assert.assertEquals("Strembicki", user.getSecondName());
    }


}
