package org.fungover.mmotodo.repositories;

import org.fungover.mmotodo.entities.category.Category;
import org.fungover.mmotodo.entities.tag.Tag;
import org.fungover.mmotodo.entities.task.Task;
import org.springframework.stereotype.Component;

import java.util.*;

// TODO - Replace with real repository!
@Component
public class TaskRepository {
    private static int counter = 1;
    private final List<Task> taskList = new ArrayList<>();

    public TaskRepository() {
        taskList.add(createTask());
        taskList.add(createTask());
        taskList.add(createTask());
    }

    public Optional<Task> findById(Integer taskId) {
        return taskList
                .stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst();
    }

    public List<Task> findAll() {
        return taskList;
    }

    public List<Task> findByCategoryId(int categoryId) {
        return taskList.stream()
                .filter(task -> task.getCategory().getId() == categoryId)
                .toList();
    }

    public List<Task> findByTagId(int tagId) {
        return taskList.stream()
                .filter(task -> task.getTag().getId() == tagId)
                .toList();
    }

    private Task createTask() {
        Task task = new Task();
        task.setId(1);
        task.setTitle("Task");
        task.setDescription("This is a test task");

        Category category = new Category();
        category.setId(1);
        category.setName("Category");
        task.setCategory(category);

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("tag1");
        tag.setDescription("test tag");
        task.setTag(tag);
        task.setStatus("STARTED");

        return task;
    }

    public Task save(Task newTask) {
        taskList.add(newTask);
        return taskList
                .stream()
                .filter(task -> task.getId().equals(newTask.getId()))
                .findFirst().orElseThrow();
    }
}
