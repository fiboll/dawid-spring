package dawid.spring.controller;

import dawid.spring.manager.IUserTable;
import dawid.spring.manager.UserManager;
import dawid.spring.model.Task;
import dawid.spring.model.User;
import dawid.spring.provider.TaskDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * Created by dawid on 09.06.17.
 */
@Controller
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserManager userManager;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    IUserTable userTable;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("users", userManager.getAllUsers());
        return "test";
    }

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String getByNick(@RequestParam String nick, Model model) {

        Optional<User> user= userManager.findUserByNick(nick);

        if (user.isPresent()) {
            model.addAttribute("user", user.get()) ;
            model.addAttribute("doing",  userTable.getDoing(user.get())) ;
            model.addAttribute("nextToDo",  userTable.getNextToDo(user.get())) ;
            model.addAttribute("done",  userTable.getDoneTasks(user.get())) ;

            model.addAttribute("newTask", new Task());
            return "userDetails";
        }
        model.addAttribute("searchNick",nick) ;
        return "noUser";

    }

    @RequestMapping(value = "/addTask",method = RequestMethod.POST)
    public String addTaskToUser(@ModelAttribute(value="task") Task task,
                                @RequestParam(required = false) String userNick,
                                Model model) {

        if (StringUtils.isEmpty(userNick)) {
            return "redirect:noUser";
        }

        Optional<User> user= userManager.findUserByNick(userNick);

        if (user.isPresent()) {
            userManager.addTaskToUSer(user.get(), task);

            model.addAttribute("nick", user.get().getNickname()) ;
            model.addAttribute("newTask", new Task());
            return "redirect:userDetails";
        }


        return "redirect:noUser";
    }


    @RequestMapping(value = "/editTask", method = RequestMethod.GET)
    public String updateTask(@RequestParam(value="taskId") Long taskId,
                    @RequestParam String userNick,
                    Model model) {
        Task task = taskDao.getTaskById(taskId).orElse(null);
        model.addAttribute("nick", userNick);
        model.addAttribute("task", task);
        return "editForm";
    }

    @RequestMapping(value = "/editTask",method = RequestMethod.POST)
    public String editTask(@ModelAttribute(value="task") Task task,
                                Model model) {

        System.out.println("task" + task);
       taskDao.update(task);
       model.addAttribute("updatedTask", task);
       return "redirect:userDetails";
    }
}
