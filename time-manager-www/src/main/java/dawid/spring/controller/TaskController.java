package dawid.spring.controller;

import dawid.spring.manager.impl.TaskManager;
import dawid.spring.model.dto.LabelDTO;
import dawid.spring.model.dto.ModifiableTaskDTO;
import dawid.spring.model.dto.TaskDTO;
import dawid.spring.provider.LabelDao;
import dawid.spring.transformer.LabelTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by private on 13.01.18.
 */
@Controller
public class TaskController {

    @Autowired
    private TaskManager taskManager;

    @Autowired
    private LabelTransformer labelTransformer;

    @Autowired
    private LabelDao labelDao;

    @ModelAttribute("allLabels")
    public List<LabelDTO> getAllLabels() {         //TODO extract
        return labelDao.getAllLabels()
                .stream()
                .map(labelTransformer::entityToDTO)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/editTask", method = RequestMethod.GET)
    public String updateTask(@RequestParam(value = "taskId") Long taskId,
                             Model model) {
        TaskDTO task = taskManager.getTask(taskId);
        model.addAttribute("task", task != null ? ModifiableTaskDTO.create().from(task) : null);
        return "editForm";
    }

    @RequestMapping(value = "/editTask", method = RequestMethod.POST)
    public String editTask(@Valid @ModelAttribute(value = "task") ModifiableTaskDTO taskDTO,
                           final BindingResult bindingResult,
                           Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("task", taskDTO);
            return "editForm";
        }

        final TaskDTO updatedTask = taskManager.updateTask(taskDTO);
        return "redirect:user?nick=" + updatedTask.getUserName();
    }

    @RequestMapping(value = "/deleteTask", method = RequestMethod.GET)
    public String deleteTask(@RequestParam(value = "taskId") Long taskId,
                             @RequestParam(value = "userName") String userName) {

        taskManager.deleteTask(taskId);
        return "redirect:user?nick=" + userName;
    }
}

