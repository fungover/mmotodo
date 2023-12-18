package org.fungover.mmotodo.rest.task;

import org.fungover.mmotodo.task.Task;
import org.fungover.mmotodo.task.TaskCreateDto;
import org.fungover.mmotodo.task.TaskService;
import org.fungover.mmotodo.task.TaskUpdateDto;
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
    public List<Task> getAllTasks(@RequestParam(required = false) Long userId,
                                                  @RequestParam(required = false) Long teamId) {
        /*if (userId != null) {
            return ResponseEntity.ok(service.findTasksByUserId(userId));
        } else if (teamId != null) {
            return ResponseEntity.ok(service.findTasksByTeamId(teamId));
        }*/
        return service.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable int id) {
        return service.getTaskById(id);
    }

    @PostMapping
    public Task createTask(@RequestBody TaskCreateDto task) {
        return service.addTask(task);
    }

    @PutMapping("")
    public Task updateTask(@RequestBody TaskUpdateDto task) {
        return service.updateTask(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable int id) {

        var success = service.deleteTask(id);

        return success ?
                ResponseEntity.ok("{\"message\": \"Task deleted successfully\"}") :
                ResponseEntity.unprocessableEntity().body("{\"message\": \"Unable to delete\"}");

    }
}

