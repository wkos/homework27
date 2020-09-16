package pl.wkos.homework27.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wkos.homework27.model.Task;
import pl.wkos.homework27.repository.TaskRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class TaskService {
    TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public void updateTask(Long id, Task newTask) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            task.get().setName(newTask.getName());
            task.get().setDescription(newTask.getDescription());
            task.get().setTaskDone((newTask.getTaskDone()));
            task.get().setStartDate(newTask.getStartDate());
            task.get().setDeadline(newTask.getDeadline());
            taskRepository.save(task.get());
        }
    }

    public Date getCurrentDate() {
        return new java.sql.Date(new java.util.Date().getTime());
    }
}
