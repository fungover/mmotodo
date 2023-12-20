package org.fungover.mmotodo.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.util.concurrent.Queues;

@Configuration
public class TaskSubscriptionConfig {

    @Bean
    public Many<TaskEvent> taskSink() {
        return Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);
    }

    @Bean
    public Flux<TaskEvent> taskFlux(Many<TaskEvent> taskEventSink) {
        return taskEventSink.asFlux();
    }
}
