package pl.wkos.homework27.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.wkos.homework27.model.Task;
import pl.wkos.homework27.repository.TaskRepository;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {
    TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Task> allTasks = taskRepository.findAll();
        model.addAttribute("tasks", allTasks);
        model.addAttribute("task", new Task());
        return "home";
    }

    @GetMapping("/wszystkie")
    public String getAllTasks(Model model) {
        List<Task> taskList = taskRepository.findAll();
        model.addAttribute("tasks", taskList);
        return "wszystkie";
    }

    @GetMapping("/dozrobienia")
    public String getToDo(Model model) {
        List<Task> toDoTasks = taskRepository.findTasksByTaskDoneEqualsOrderByDeadline(false);
        model.addAttribute("tasks", toDoTasks);
        return "dozrobienia";
    }

    @GetMapping("/zrobione")
    public String done(Model model) {
        List<Task> done = taskRepository.findTasksByTaskDoneEqualsOrderByDeadline(true);
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
    public String addTask(Task task, Model model) {
        System.out.println(task);
//        if(task.getDeadline().equals("")) task.setDeadline(null);
        taskRepository.save(task);
        System.out.println(task);
        return "redirect:/dodaj";
    }

    @GetMapping("/usun/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/edytuj/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
        }
        System.out.println("jestem");
        return "edytuj";
    }

    @PostMapping("/edytuj/{id}")
    public String updateTask(@PathVariable("id") Long id, Task newTask) {
//        System.out.println(id);
//        if (result.hasErrors()) {
//            return "home";
//        }
        Optional<Task> task = taskRepository.findById(id);
        System.out.println("jestem2");
        System.out.println(task);
        System.out.println("jestem3");
        if (task.isPresent()) {
            task.get().setName(newTask.getName());
            task.get().setDescription(newTask.getDescription());
            task.get().setStartDate(newTask.getStartDate());
            task.get().setDeadline(newTask.getDeadline());
            System.out.println("zapisuje");
            taskRepository.save(task.get());
        }
//        System.out.println("jestem4");
////        model.addAttribute("tasks", taskRepository.findAll());
//        System.out.println("jestem5");
        return "redirect:/";
    }
//    @PostMapping("/aktualizuj")
//    public String updateTask(Long id, Model model){
//        Optional<Task> task = taskRepository.s;
//        model.addAttribute("task", task);
//        return "/aktualizuj";
//    }

    @GetMapping("/lista")
    public String lista(Model model) {
        List<Task> allTasks = taskRepository.findAll();
        model.addAttribute("tasks", allTasks);
        return "lista";
    }
//
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setLenient(false);
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
//    }
}
