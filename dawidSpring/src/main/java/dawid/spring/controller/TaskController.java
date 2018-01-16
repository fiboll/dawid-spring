package dawid.spring.controller;

import dawid.spring.model.Task;
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
    private TaskDao taskDao;

    @RequestMapping(value = "/editTask", method = RequestMethod.GET)
    public String updateTask(@RequestParam(value="taskId") Long taskId,
                             Model model) {
        Task task = taskDao.getTaskById(taskId).orElse(null);
        model.addAttribute("task", task);
        return "editForm";
    }

    @RequestMapping(value = "/editTask", method = RequestMethod.POST)
    public String editTask(@ModelAttribute(value = "task") Task task,
                           final BindingResult bindingResult,
                           Model model) {

        Task updated = taskDao.getTaskById(task.getId()).get();

        updated.setDone(task.isDone());
        updated.setDueDate(task.getDueDate());
        updated.setDesc(task.getDesc());
        updated.setName(task.getName());

        taskDao.update(updated);
        return "redirect:user?nick=" + updated.getUser().getNickname();
    }

    @RequestMapping(value = "/deleteTask", method = RequestMethod.GET)
    public String deleteTask(@RequestParam(value="taskId") Long taskId,
                           Model model) {
        Task task = taskDao.getTaskById(taskId).get();

        String userName = task.getUser().getNickname();

        taskDao.removeTask(task);
        return "redirect:user?nick=" + userName;
    }
}

