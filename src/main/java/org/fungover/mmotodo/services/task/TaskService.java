package org.fungover.mmotodo.services.task;

import jakarta.persistence.EntityNotFoundException;
import org.fungover.mmotodo.dto.TaskCreate;
import org.fungover.mmotodo.dto.TaskUpdate;
import org.fungover.mmotodo.entities.task.Task;
import org.fungover.mmotodo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return taskRepository.findAllByCategoryId(categoryId);
    }

    public List<Task> getTasksByTagId(int tagId) {
        return taskRepository.findAllByTagId(tagId);
    }

    @Transactional
    public void deleteTask(int taskId) {
        taskRepository.deleteById(taskId);
    }

    @Transactional
    public Task updateTask(TaskUpdate taskUpdate) {
        Task task = taskRepository.findById(taskUpdate.id()).orElseThrow();

        if (taskUpdate.title() != null) task.setTitle(task.getTitle());
        if (taskUpdate.description() != null) task.setDescription(task.getDescription());
        if (taskUpdate.status() != null) task.setStatus(task.getStatus());

       /* if (taskUpdate.categoryId() != null) {
            Category category = categoryRepository.findById(taskUpdate.categoryId()).orElseThrow();
            task.setCategory(category);
        }
        if (taskUpdate.tagId() != null) {
            Tag tag = tagRepository.findById(taskUpdate.tagId()).orElseThrow();
            task.setTag(tag);
        }*/
        if (taskUpdate.description() != null) task.setDescription(task.getDescription());


        return taskRepository.save(Task.of(taskUpdate));
    }
}

