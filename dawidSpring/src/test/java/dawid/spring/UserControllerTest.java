package dawid.spring;

import dawid.spring.manager.IUserTable;
import dawid.spring.manager.UserManager;
import dawid.spring.model.Task;
import dawid.spring.model.User;
import javafx.beans.binding.StringBinding;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by dawid on 09.06.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
        (
                {
                        "classpath:**/applicationContext.xml",
                        "classpath:**/context_test.xml"
                }
        )
@Transactional
@Rollback
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    UserManager userManager;

    @Autowired
    IUserTable userTable;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void getAllTest() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/");
        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("test"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", IsCollectionWithSize.hasSize(2)));
    }

    @Test
    public void getNotExistUserTest() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/user?nick=nonexist");
        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("noUser"));
    }

    @Test
    public void getUserTest() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/user?nick=fiboll");
        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("backlog"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("doing"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("nextToDo"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("done"))
                .andExpect(MockMvcResultMatchers.view().name("userDetails"));
    }

    @Test
    public void addTaskToNoneExistUser() throws Exception {

        RequestBuilder builder = MockMvcRequestBuilders.post("/addTask")
                .param("name", "test")
                .param("desc", "testDesc")
                .param("userNick", "fiboll23");


        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.view().name("redirect:noUser"));
    }

    @Test
    public void addTaskUser() throws Exception {

        RequestBuilder builder = MockMvcRequestBuilders.post("/addTask")
                .param("name", "test")
                .param("desc", "testDesc")
                .param("userNick", "fiboll");
        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("nick"))
                .andExpect(MockMvcResultMatchers.model().attribute("nick", "fiboll"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:userDetails"));
    }

    @Test
    public void addTaskUserNoNick() throws Exception {

        RequestBuilder builder = MockMvcRequestBuilders.post("/addTask")
                .param("name", "test")
                .param("desc", "testDesc");

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.view().name("redirect:noUser"));
    }

    @Test
    public void testGetEditTaskNotExist() throws Exception {

        RequestBuilder builder = MockMvcRequestBuilders.get("/editTask")
                .param("taskId", String.valueOf(100L))
                .param("userNick", "fiboll");

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.view().name("editForm"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("nick"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("task"));


    }

    @Test
    public void testEditTaskPost() throws Exception {
        Optional<User> user = userManager.findUserByNick("fiboll");
        Assert.assertTrue(user.isPresent());

        Optional<Task> task = userTable.getNextToDo(user.get()).stream().findAny();
        Assert.assertTrue(task.isPresent());

        System.out.println("original task: " + task.get());

        RequestBuilder builder = MockMvcRequestBuilders.post("/editTask")
                .param("id", String.valueOf(task.get().getId()))
                .param("desc", "edit test task");
                //.param("dueDate", task.get().getDueDate().toString());

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print());



    }
}
