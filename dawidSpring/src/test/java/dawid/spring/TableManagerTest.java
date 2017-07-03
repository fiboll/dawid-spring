package dawid.spring;

import dawid.spring.controller.UserController;
import dawid.spring.manager.TableManager;
import dawid.spring.model.Task;
import dawid.spring.model.User;
import dawid.spring.provider.UserDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by dawid on 03.07.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context_test.xml")
@Transactional
@Rollback
public class TableManagerTest {

    @Autowired
    private TableManager tableManager;

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testDoneTask() {
        User user = userDAO.findByNick("fiboll").
                orElseThrow(() -> new IllegalStateException("User do not exist"));
        Task task = user.getTable().getDoing().getTasks().iterator().next();
        tableManager.doneTask(user.getTable(), task);
        Assert.assertTrue(user.getTable().getDone().getTasks().contains(task));
        Assert.assertTrue(!user.getTable().getDoing().getTasks().contains(task));
    }
}
