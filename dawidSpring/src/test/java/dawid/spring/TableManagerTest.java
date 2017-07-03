package dawid.spring;

import dawid.spring.manager.TableManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    public void testDoneTask() {

    }
}
