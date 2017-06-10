package dawid.spring;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context_test.xml")
public class AppTest {

    @Autowired
    ApplicationContext context;

    @Test
    public void testName() {
        HelloWorld bean = context.getBean("firstBean", HelloWorld.class);
        Assert.assertEquals("Dawid", bean.getName());
    }
}
