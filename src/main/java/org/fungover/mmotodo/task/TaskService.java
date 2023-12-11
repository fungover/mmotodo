package org.fungover.mmotodo.task;

import jakarta.validation.Valid;
import org.fungover.mmotodo.category.Category;
import org.fungover.mmotodo.category.CategoryRepository;
import org.fungover.mmotodo.exception.CategoryNotFoundException;
import org.fungover.mmotodo.exception.ResourceNotFoundException;
import org.fungover.mmotodo.exception.TagNotFoundException;
import org.fungover.mmotodo.exception.TaskNotFoundException;
import org.fungover.mmotodo.tag.Tag;
import org.fungover.mmotodo.tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Autowired
    public TaskService(
            TaskRepository taskRepository,
            CategoryRepository categoryRepository,
            TagRepository tagRepository
    ) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    public Task getTaskById(int id) {
        return taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
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
    public Task addTask(TaskCreate taskCreate) {
        Task task = new Task();

        task.setTitle(taskCreate.title());
        task.setDescription(taskCreate.description());
        task.setTimeEstimation(0.0);
        task.setDueDate(LocalDateTime.now().plusSeconds(5 * 60));
        Tag tag = tagRepository.findById(1).orElseThrow(TagNotFoundException::new);
        task.setTag(tag);
        Category category = categoryRepository.findById(1).orElseThrow(CategoryNotFoundException::new);
        task.setCategory(category);

        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(int taskId) {
        taskRepository.deleteById(taskId);
    }

    @Transactional
    public boolean updateTask(@Valid TaskUpdate taskUpdate) {
        Task task = taskRepository.findById(taskUpdate.id()).orElseThrow(TaskNotFoundException::new);

        if (!taskUpdate.title().isEmpty()) task.setTitle(task.getTitle());
        if (!taskUpdate.description().isEmpty()) task.setDescription(task.getDescription());
        if (!taskUpdate.status().isEmpty()) task.setStatus(task.getStatus());

        Tag tag = tagRepository.findById(taskUpdate.tagId()).orElseThrow(TagNotFoundException::new);
        task.setTag(tag);

        Category category = categoryRepository.findById(taskUpdate.categoryId()).orElseThrow(CategoryNotFoundException::new);
        task.setCategory(category);

        taskRepository.save(task);
        return true;
    }
}
