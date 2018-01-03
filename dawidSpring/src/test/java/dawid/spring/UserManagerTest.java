package dawid.spring;

import dawid.spring.manager.UserManager;
import dawid.spring.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context_test.xml")
@Transactional
@Rollback
public class UserManagerTest {

    @Autowired
    private UserManager userManager;

    @Test
    public void testFindAll() {
        List<User> users =  userManager.getAllUsers();
        Assert.assertNotNull(users);
        Assert.assertEquals(2, users.size());

        users.forEach(
                (User u) -> Assert.assertTrue(null != u.getId())
        );
    }

}
