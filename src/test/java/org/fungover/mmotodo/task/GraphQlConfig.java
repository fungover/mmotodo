package org.fungover.mmotodo.task;

import com.tailrocks.graphql.datetime.LocalDateTimeScalar;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@TestConfiguration
public class GraphQlConfig {
    @Bean
    public RuntimeWiringConfigurer graphqlDateTimeConfigurer() {
        return (builder) ->
                builder.scalar(LocalDateTimeScalar.create(null, false, null));
    }
}
