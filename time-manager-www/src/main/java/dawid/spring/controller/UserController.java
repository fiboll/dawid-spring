package dawid.spring.controller;

import dawid.spring.manager.IUserTable;
import dawid.spring.manager.UserManager;
import dawid.spring.model.dto.LabelDTO;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.dto.UserDTO;
import dawid.spring.provider.LabelDao;
import dawid.spring.transformer.ILabelTransformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;

/**
 * Created by dawid on 09.06.17.
 */
@Controller
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserManager userManager;

    @Autowired
    private IUserTable userTable;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private ILabelTransformer labelTransformer;

    @ModelAttribute("allLabels")
    public List<LabelDTO> getAllLabels() {         //TODO extract
        return labelDao.getAllLabels()
                .stream()
                .map(labelTransformer::entityToDTO)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("users", userManager.getAllUsers());
        return "test";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getByNick(@RequestParam String nick, Model model) {

        Optional<UserDTO> user = userManager.findUserByNick(nick);

        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("backlog", userTable.getBacklogTasks(user.get()));
            model.addAttribute("doing", userTable.getDoingTasks(user.get()));
            model.addAttribute("nextToDo", userTable.getNextToDoTasks(user.get()));
            model.addAttribute("done", userTable.getDoneTasks(user.get()));

            model.addAttribute("newTask", prepareNewTask(nick));
            return "userDetails";
        }
        model.addAttribute("searchNick", nick);
        return "noUser";

    }

    @RequestMapping(value = "/addTask", method = RequestMethod.POST)
    public String addTaskToUser(@ModelAttribute(value = "task") TaskDTO task,
            @RequestParam(required = false) String userNick,
            Model model) {

        if (StringUtils.isEmpty(userNick)) {
            return "redirect:noUser";
        }

        Optional<UserDTO> user = userManager.findUserByNick(userNick);

        if (user.isPresent()) {
            final TaskDTO addedTask = task.withUserName(user.get().getNickname());
            userManager.addTaskToUSer(user.get(), addedTask);

            model.addAttribute("nick", user.get().getNickname());
            model.addAttribute("newTask", prepareNewTask(userNick));
            return "redirect:user";
        }

        return "redirect:noUser";
    }

    private TaskDTO prepareNewTask(String userName) {
        return TaskDTO.builder().userName(userName)
                .name("")
                .isDone(false)
                .dueDate(new Date())
                .desc("")
                .version(1L)
                .id(1L)
                .labels(emptySet())
                .build();
    }
}
