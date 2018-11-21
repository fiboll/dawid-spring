package controller;

import dawid.spring.model.dto.ImmutableUserDTO;
import dawid.spring.model.dto.ModifiableUserDTO;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;

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
public class RegistrationControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void testUserRegistrationGet() throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.get("/user/registration");
        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("registration"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));
    }

    @Test
    public void testUserRegistrationPost() throws Exception {
        final ImmutableUserDTO user = ImmutableUserDTO.builder()
                .email("test@op.pl")
                .firstName("test")
                .id(1L)
                .nickname("test")
                .password("test123")
                .matchingPassword("test123")
                .tasks(emptySet())
                .secondName("test")
                .version(1L)
                .build();


        RequestBuilder builder = MockMvcRequestBuilders.post("/user/registration")
            .flashAttr("user", ModifiableUserDTO.create().from(user));

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("userDetails"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("newTask"));

    }
}

