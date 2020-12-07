package controller;

import dawid.spring.manager.IUserTable;
import dawid.spring.manager.impl.TaskManager;
import dawid.spring.manager.UserManager;
import dawid.spring.model.dto.*;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by private on 13.01.18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
        (
                {
                        "classpath:context_test.xml",
                        "file:src/main/webapp/WEB-INF/applicationContext.xml",
                }
        )
@Rollback
public class TaskControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private UserManager userManager;

    @Autowired
    private TaskManager taskManager;

    @Autowired
    private IUserTable userTable;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void testEditTaskPost() throws Exception {
        Optional<UserDTO> user = userManager.findUserByNick("fiboll");
        assertTrue(user.isPresent());

        Optional<TaskDTO> task = userTable.getNextToDoTasks(user.get()).stream().findAny();
        assertTrue(task.isPresent());

        RequestBuilder builder = MockMvcRequestBuilders.post("/editTask")
                .param("id", String.valueOf(task.get().getId()))
                .param("desc", "edit test task")
                .param("name", "test")
                .param("done", "false")
                .param("userName", "Dawid")
                .param("labels", "A1")
                .param("dueDate", "2018-12-12");
        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print());

        task = userTable.getNextToDoTasks(userManager.findUserByNick("fiboll").get()).stream().findAny();
        assertTrue(task.isPresent());
        assertEquals("edit test task", task.get().getDesc());
        assertEquals("test", task.get().getName());
        assertTrue(!task.get().isDone());
        assertEquals(Set.of("A1"), task.get().getLabels().stream().map(LabelDTO::getDescription).collect(Collectors.toSet()));
        assertEquals(new SimpleDateFormat(  "dd/MM/yyyy").parse("12/12/2018"), task.get().getDueDate());
    }

    @Test
    public void addTaskToNoneExistUser() throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.post("/addTask")
                .param("userNick", "fiboll23")
                .flashAttr("task", ModifiableTaskDTO.create().from(prepareTask()));

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.view().name("redirect:noUser"));
    }

    @Test
    public void addTaskUser() throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.post("/addTask")
                .param("userNick", "fiboll")
                .flashAttr("task", prepareTask());

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("nick"))
                .andExpect(MockMvcResultMatchers.model().attribute("nick", "fiboll"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:user"));

        Optional<UserDTO> user = userManager.findUserByNick("fiboll");
        assertTrue(user.isPresent());

        final Optional<TaskDTO> addedTask = user.get().getTasks().stream()
                .filter(taskDTO -> taskDTO.getName().equals("new task"))
                .findFirst();

        assertTrue(addedTask.isPresent());

        assertEquals("desc", addedTask.get().getDesc());
        assertEquals("new task", addedTask.get().getName());
        assertEquals(0L, addedTask.get().getVersion().longValue());
        assertTrue(!addedTask.get().isDone());
        assertTrue(addedTask.get().getLabels().isEmpty());
        assertEquals(new SimpleDateFormat(  "dd/MM/yyyy").parse("12/12/2018"), addedTask.get().getDueDate());

    }

    @Test
    public void addTaskUserNoNick() throws Exception {

        RequestBuilder builder = MockMvcRequestBuilders.post("/addTask")
                .param("name", "test")
                .flashAttr("task", prepareTask());

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.view().name("redirect:noUser"));
    }

    @Test
    public void testGetEditTaskNotExist() throws Exception { //TODO error message

//        RequestBuilder builder = MockMvcRequestBuilders.get("/editTask")
//                .param("taskId", String.valueOf(100L))
//                .param("userNick", "fiboll");
//
//        mockMvc.perform(builder)
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.view().name("editForm"));
//                .andExpect(MockMvcResultMatchers.model().attributeExists("nick"))
//                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("task"));

    }

    @Test
    public void testDeleteTask() throws Exception {
        Optional<UserDTO> user = userManager.findUserByNick("fiboll");
        assertTrue(user.isPresent());

        Optional<TaskDTO> task = userTable.getNextToDoTasks(user.get()).stream().findAny();
        assertTrue(task.isPresent());

        Long deletedId = task.get().getId();

        RequestBuilder builder = MockMvcRequestBuilders.get("/deleteTask")
                .param("taskId", String.valueOf(task.get().getId()))
                .param("userName", "fiboll");

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.view().name("redirect:user?nick=fiboll"));


        Optional<TaskDTO> deletedTask = Optional.ofNullable(taskManager.getTask(deletedId));
        assertTrue(!deletedTask.isPresent());

        user = userManager.findUserByNick("fiboll");
        assertTrue(user.isPresent());

        Optional<TaskDTO> notExistTask = user.get().getTasks().stream()
                .filter((t) -> t.getId().equals(deletedId))
                .findAny();

        assertTrue(!notExistTask.isPresent());
    }

    private TaskDTO prepareTask() throws ParseException {
        final ImmutableTaskDTO taskDTO = ImmutableTaskDTO.builder().desc("desc")
                .dueDate(new SimpleDateFormat("dd/MM/yyyy").parse("12/12/2018"))
                .id(0L)
                .done(false)
                .name("new task")
                .userName("fiboll")
                .desc("desc")
                .version(1L)
                .build();
        return ModifiableTaskDTO.create().from(taskDTO);
    }

}
