package org.fungover.mmotodo.repositories;

import org.fungover.mmotodo.entities.tag.Tag;
import org.fungover.mmotodo.entities.task.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

// TODO - Replace with real repository!
@Component
public class TaskRepository {
    private static int counter = 1;
    private final List<Task> taskList;

    public TaskRepository() {
        this.taskList = List.of(createTask(), createTask(), createTask());
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

    public Task addTask() {
        return null;
    }

    private Task createTask() {
        Task task = new Task();
        task.setId(counter);
        task.setTitle("Task");
        task.setDescription("This is a test task");
        Tag tag = new Tag();
        tag.setName("tag1");
        tag.setDescription("test tag");
        task.setTag(tag);
        task.setStatus("DOING");
        counter++;
        return task;
    }
}
