package org.fungover.mmotodo.task;

import jakarta.validation.Valid;
import org.fungover.mmotodo.category.Category;
import org.fungover.mmotodo.category.CategoryRepository;
import org.fungover.mmotodo.exception.CategoryNotFoundException;
import org.fungover.mmotodo.exception.TagNotFoundException;
import org.fungover.mmotodo.exception.TaskNotFoundException;
import org.fungover.mmotodo.tag.Tag;
import org.fungover.mmotodo.tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public Task addTask(TaskCreateDto taskCreate) {
        Task task = new Task();

        task.setTitle(taskCreate.title());
        task.setDescription(taskCreate.description());
        task.setStatus(taskCreate.status());
        task.setTimeEstimation(0.0);
        task.setDueDate(LocalDateTime.now().plusSeconds(TimeUnit.MINUTES.toSeconds(5)));
        Tag tag = tagRepository.findById(1).orElseThrow(TagNotFoundException::new);
        task.setTag(tag);
        Category category = categoryRepository.findById(1).orElseThrow(CategoryNotFoundException::new);
        task.setCategory(category);

        return taskRepository.save(task);
    }

    @Transactional
    public boolean deleteTask(int taskId) {
        var optTask = taskRepository.findById(taskId);
        if(optTask.isPresent()) {
            taskRepository.deleteById(taskId);
            return true;
        }
        return false;
    }

    @Transactional
    public Task updateTask(@Valid TaskUpdateDto taskUpdate) {
        Task task = taskRepository.findById(taskUpdate.id()).orElseThrow(TaskNotFoundException::new);

        if (taskUpdate.title() != null) task.setTitle(taskUpdate.title());
        if (taskUpdate.description() != null) task.setDescription(task.getDescription());
        if (taskUpdate.status() != null) task.setStatus(taskUpdate.status());

        if (taskUpdate.tagId() != null) {
            Tag tag = tagRepository.findById(taskUpdate.tagId()).orElseThrow(TagNotFoundException::new);
            task.setTag(tag);
        }

        if (taskUpdate.categoryId() != null) {
            Category category = categoryRepository.findById(taskUpdate.categoryId()).orElseThrow(CategoryNotFoundException::new);
            task.setCategory(category);
        }

        return taskRepository.save(task);
    }
}

