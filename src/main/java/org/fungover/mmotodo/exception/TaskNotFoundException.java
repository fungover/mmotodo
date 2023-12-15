package org.fungover.mmotodo.exception;

public class TaskNotFoundException extends ResourceNotFoundException {
    public TaskNotFoundException() {
        super("Task not found");
    }
}
