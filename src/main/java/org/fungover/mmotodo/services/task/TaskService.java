package org.fungover.mmotodo.services.task;

import org.fungover.mmotodo.dto.TaskCreate;
import org.fungover.mmotodo.entities.task.Task;
import org.fungover.mmotodo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

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

    @Transactional
    public Task addTask(TaskCreate newTask) {
        Task task = Task.of(newTask);
        return taskRepository.save(task);
    }
}

