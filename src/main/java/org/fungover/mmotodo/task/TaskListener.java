package org.fungover.mmotodo.task;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks.Many;

@Component
public class TaskListener {
    Many<TaskEvent> taskSink;

    public TaskListener(Many<TaskEvent> taskEventSink) {
        this.taskSink = taskEventSink;
    }

    @EventListener
    void handleTaskCreatedEvent(TaskEvent event) {
        taskSink.tryEmitNext(event);
    }
}
