package dawid.spring;

import dawid.spring.provider.UserManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "context.xml");

       // HelloWorld obj = (HelloWorld) context.getBean("firstBean");
        UserManager userManager = (UserManager) context.getBean("userManager");
        userManager.getAllUsers();
       // obj.printHello();
    }
}
