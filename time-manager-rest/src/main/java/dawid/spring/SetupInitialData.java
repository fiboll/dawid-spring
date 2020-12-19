package dawid.spring;

import dawid.spring.manager.UserManager;
import dawid.spring.manager.impl.TaskManager;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Dawid Strembicki Hycom (dawid.strembicki.hycom@hycom.pl)
 */
@Component
public class SetupInitialData implements CommandLineRunner {

    @Autowired
    private TaskManager taskManager;

    @Autowired
    private UserManager userManager;
    @Override
    public void run(String... args) {
        TaskDTO task1= TaskDTO.builder()
                .name("Laundry")
                .desc("Do the laundry")
                .build();

        TaskDTO task2 = TaskDTO.builder()
                .name("Read")
                .desc("Read an article")
                .build();

        TaskDTO task3 = TaskDTO.builder()
                .name("Running")
                .desc("30 minuts running")
                .build();

        UserDTO userDTO = UserDTO.builder()
                .firstName("Dawid")
                .secondName("Strembicki")
                .email("fiboll@o2.pl")
                .nickname("fiboll")
                .password("1234")
                .matchingPassword("1234")
                //.tasks(Set.of(task1, task2, task3))
                .build();

        userDTO = userManager.registerNewUserAccount(userDTO);
        userDTO = userManager.addTaskToUSer(userDTO, task1);
        userDTO = userManager.addTaskToUSer(userDTO, task2);
        userDTO = userManager.addTaskToUSer(userDTO, task3);

    }
}
