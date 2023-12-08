package org.fungover.mmotodo.controllers.task;

import jakarta.validation.Valid;
import org.fungover.mmotodo.dto.TaskCreate;
import org.fungover.mmotodo.dto.TaskUpdate;
import org.fungover.mmotodo.entities.task.Task;
import org.fungover.mmotodo.services.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@Controller
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
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
    @ResponseStatus(HttpStatus.CREATED)
    public Task addTask(@Valid @Argument TaskCreate task) {
         return taskService.addTask(task);
    }

    @MutationMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@Argument int id) {
        taskService.deleteTask(id);
    }

    @MutationMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Task updateTask(@Argument @Valid TaskUpdate task){
        return taskService.updateTask(task);
    }
}
