package org.fungover.mmotodo.services.task;

import org.fungover.mmotodo.entities.task.Task;
import org.fungover.mmotodo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTaskById(int id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByCategoryId(int categoryId) {
        return taskRepository.findByCategoryId(categoryId);
    }

    public List<Task> getTasksByTagId(int tagId) {
        return taskRepository.findByTagId(tagId);
    }
}
