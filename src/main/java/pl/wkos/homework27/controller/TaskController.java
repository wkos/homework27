package pl.wkos.homework27.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.wkos.homework27.model.Task;
import pl.wkos.homework27.service.TaskService;

import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {
    TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Task> allTasks = taskService.getTaskRepository().findAllByOrderByDeadline();
        model.addAttribute("tasks", allTasks);
        model.addAttribute("task", new Task());
        model.addAttribute("currentDate", taskService.getCurrentDate());
        return "home";
    }

    @GetMapping("/wszystkie")
    public String getAllTasks(Model model) {
        List<Task> taskList = taskService.getTaskRepository().findAllByOrderByDeadline();
        model.addAttribute("tasks", taskList);
        return "wszystkie";
    }

    @GetMapping("/dozrobienia")
    public String getToDo(Model model) {
        List<Task> toDoTasks = taskService.getTaskRepository().findTasksByTaskDoneEqualsOrderByDeadline(false);
        model.addAttribute("tasks", toDoTasks);
        return "dozrobienia";
    }

    @GetMapping("/zrobione")
    public String done(Model model) {
        List<Task> done = taskService.getTaskRepository().findTasksByTaskDoneEqualsOrderByDeadline(true);
        model.addAttribute("tasks", done);
        return "zrobione";
    }

    @GetMapping("/dodaj")
    public String formAddTask(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        return "dodaj";
    }

    @PostMapping("/dodaj")
    public String addTask(Task task) {
        taskService.getTaskRepository().save(task);
        return "redirect:/dodaj";
    }

    @GetMapping("/usun/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskService.getTaskRepository().deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/edytuj/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Task> task = taskService.getTaskRepository().findById(id);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
        }
        return "edytuj";
    }

    @PostMapping("/edytuj/{id}")
    public String editTask(@PathVariable("id") Long id, Task newTask) {
        taskService.updateTask(id, newTask);
        return "redirect:/";
    }
}
