import dawid.spring.provider.TaskDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.stream.Stream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context.xml")
public class Test {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired TaskDao taskDao;

    @Before
    public void before() {
        System.out.println("------------------------");
        Stream.of(applicationContext.getBeanDefinitionNames())
                .forEach(System.out::println);
        System.out.println("------------------------");
    }

    @org.junit.Test
    public void test() {
        Assert.assertTrue(taskDao.getTaskById(1L) != null);
    }
}

