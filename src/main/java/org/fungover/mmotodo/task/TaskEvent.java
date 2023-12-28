package org.fungover.mmotodo.task;

public record TaskEvent(Task task, TaskAction action) {
}