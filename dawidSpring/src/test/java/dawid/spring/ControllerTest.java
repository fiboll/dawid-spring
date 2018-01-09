package dawid.spring;

import dawid.spring.manager.UserManager;
import org.hamcrest.collection.IsCollectionWithSize;
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
public class ControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    UserManager userManager;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void getAllTest() throws Exception{
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/");
        mockMvc.perform(builder)
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("test"))
               .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
               .andExpect(MockMvcResultMatchers.model().attribute("users", IsCollectionWithSize.hasSize(2)) );
    }

    @Test
    public void getNotExistUserTest() throws Exception{
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/user?nick=nonexist");
        mockMvc.perform(builder)
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("noUser"));
    }

    @Test
    public void getUserTest() throws Exception{
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/user?nick=fiboll");
        mockMvc.perform(builder)
               .andExpect(MockMvcResultMatchers.status().isOk())
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
               .andExpect(MockMvcResultMatchers.view().name("redirect:user"));
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
               .andExpect(MockMvcResultMatchers.model().attributeExists("nick"))
               .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("task"));


    }
}
