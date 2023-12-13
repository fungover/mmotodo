package org.fungover.mmotodo;


import org.fungover.mmotodo.entities.task.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> findAllTasks() {
        return repository.findAll();
    }

    public List<Task> findTasksByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public List<Task> findTasksByTeamId(Long teamId) {
        return repository.findByTeamId(teamId);
    }

    public Task findTaskById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Task saveTask(Task task) {
        return repository.save(task);
    }

    public Task updateTask(Long id, Task task) {
        task.setId(id);
        return repository.save(task);
    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
    }
}
