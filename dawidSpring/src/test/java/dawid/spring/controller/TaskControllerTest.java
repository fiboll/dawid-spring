package dawid.spring.controller;

import dawid.spring.manager.IUserTable;
import dawid.spring.manager.TaskManager;
import dawid.spring.manager.UserManager;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

/**
 * Created by private on 13.01.18.
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
@Rollback
public class TaskControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    UserManager userManager;

    @Autowired
    TaskManager taskManager;

    @Autowired
    IUserTable userTable;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void testEditTaskPost() throws Exception {
        Optional<UserDTO> user = userManager.findUserByNick("fiboll");
        Assert.assertTrue(user.isPresent());

        Optional<TaskDTO> task = userTable.getNextToDo(user.get()).stream().findAny();
        Assert.assertTrue(task.isPresent());

        RequestBuilder builder = MockMvcRequestBuilders.post("/editTask")
                .param("id", String.valueOf(task.get().getId()))
                .param("desc", "edit test task");
        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print());
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
    public void testDeleteTask() throws Exception {
        Optional<UserDTO> user = userManager.findUserByNick("fiboll");
        Assert.assertTrue(user.isPresent());

        Optional<TaskDTO> task = userTable.getNextToDo(user.get()).stream().findAny();
        Assert.assertTrue(task.isPresent());

        Long deletedId = task.get().getId();

        System.out.println("deletedId " + deletedId);

        RequestBuilder builder = MockMvcRequestBuilders.get("/deleteTask")
                .param("taskId", String.valueOf(task.get().getId()))
                .param("userName", "fiboll");

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.view().name("redirect:user?nick=fiboll"));

        user = userManager.findUserByNick("fiboll");

        Optional<TaskDTO> notExistTask= user.get().getTasks().stream()
                .filter((t) -> t.getId() == deletedId)
                .findAny();

        Assert.assertTrue(!notExistTask.isPresent());

        Optional<TaskDTO> deletedTask = Optional.ofNullable(taskManager.getTask(deletedId));
        Assert.assertTrue(!notExistTask.isPresent());

    }

}
