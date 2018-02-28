package dawid.spring.controller;

import dawid.spring.manager.TaskManager;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.model.entity.Task;
import dawid.spring.provider.TaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by private on 13.01.18.
 */
@Controller
public class TaskController {

    @Autowired
    private TaskManager taskManager;

    @RequestMapping(value = "/editTask", method = RequestMethod.GET)
    public String updateTask(@RequestParam(value = "taskId") Long taskId,
                             Model model) {
        TaskDTO task = taskManager.getTask(taskId);
        model.addAttribute("task", task);
        return "editForm";
    }

    @RequestMapping(value = "/editTask", method = RequestMethod.POST)
    public String editTask(@ModelAttribute(value = "task") TaskDTO taskDTO,
                           final BindingResult bindingResult,
                           Model model) {

        taskDTO = taskManager.updateTask(taskDTO);
        return "redirect:user?nick=" + taskDTO.getUserName();
    }

    @RequestMapping(value = "/deleteTask", method = RequestMethod.GET)
    public String deleteTask(@RequestParam(value = "taskId") Long taskId,
                             @RequestParam(value = "userName") String userName,
                             Model model) {

        taskManager.deleteTask(taskId);
        return "redirect:user?nick=" + userName;
    }
}

