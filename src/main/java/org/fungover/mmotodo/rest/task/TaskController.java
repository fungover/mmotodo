package org.fungover.mmotodo.rest.task;

import org.fungover.mmotodo.task.Task;
import org.fungover.mmotodo.task.TaskCreateDto;
import org.fungover.mmotodo.task.TaskService;
import org.fungover.mmotodo.task.TaskUpdateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam(required = false) Long userId,
                                                  @RequestParam(required = false) Long teamId) {
        if (userId != null) {
            return ResponseEntity.ok(service.findTasksByUserId(userId));
        } else if (teamId != null) {
            return ResponseEntity.ok(service.findTasksByTeamId(teamId));
        }
        return ResponseEntity.ok(service.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        Task task = service.getTaskById(id);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskCreateDto task) {
        return ResponseEntity.ok(service.addTask(task));
    }

    @PutMapping("")
    public ResponseEntity<Task> updateTask(@RequestBody TaskUpdateDto task) {
        return ResponseEntity.ok(service.updateTask(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable int id) {
        try {

            service.deleteTask(id);
            return ResponseEntity.ok().body("{\"message\": \"Task deleted successfully\"}");
        }catch(Exception e) {
            // log exception here
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Error occurred while deleting the task.\"}")
        }
    }
}
}
