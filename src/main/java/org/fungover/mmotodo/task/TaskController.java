package org.fungover.mmotodo.task;

import jakarta.validation.Valid;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;

@Controller
public class TaskController {
    private final TaskService taskService;
    private final Flux<TaskEvent> taskEvent;

    public TaskController(TaskService taskService, Flux<TaskEvent> taskEvent) {
        this.taskService = taskService;
        this.taskEvent = taskEvent;
    }

    @QueryMapping
    public Task taskById(@Argument int id) {
        return taskService.getTaskById(id);
    }

    @QueryMapping
    public List<Task> allTasks() {
        return taskService.getAllTasks();
    }

    @QueryMapping
    public List<Task> tasksByCategoryId(@Argument int categoryId) {
        return taskService.getTasksByCategoryId(categoryId);
    }

    @QueryMapping
    public List<Task> tasksByTagId(@Argument int tagId) {
        return taskService.getTasksByTagId(tagId);
    }

    @MutationMapping
    public Task addTask(@Valid @Argument TaskCreateDto task) {
        return taskService.addTask(task);
    }

    @MutationMapping
    public String deleteTask(@Argument int id) {
        if(taskService.deleteTask(id)) {
            return "Task with id: " + id + " is deleted";
        }
        return "Could not delete task with id " + id;
    }

    @MutationMapping
    public Task updateTask(@Argument @Valid TaskUpdateDto task){
        return taskService.updateTask(task);
    }

    @SubscriptionMapping
    public Flux<TaskEvent> taskEvent() {
        return taskEvent;
    }
}
