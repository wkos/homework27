package pl.wkos.homework27.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wkos.homework27.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findTasksByTaskDoneEqualsOrderByDeadline(Boolean toDo);
}
