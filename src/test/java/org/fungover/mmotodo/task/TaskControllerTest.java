package org.fungover.mmotodo.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fungover.mmotodo.category.Category;
import org.fungover.mmotodo.tag.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.ArrayList;
import java.util.List;


@GraphQlTest
@Import(value = {GraphQlConfig.class})
class TaskControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private final List<Task> tasks = new ArrayList<>();

    private static int counter = 1;

    @BeforeEach
    void setUp() {
        tasks.add(createTask("Something"));
        tasks.add(createTask("Nice"));
        Mockito.when(taskService.getAllTasks()).thenReturn(tasks);
    }

    @Test
    void ShouldRespondWithAllTaskTitles() throws Exception {
        // language=GraphQL
        String query = "query { allTasks { title } }";

        var expectedList = tasks.stream().map(task -> new Object() {
            public final String title = task.getTitle();
        }).toList();

        String expected = objectMapper.writeValueAsString(expectedList);

        graphQlTester.document(query)
                .execute()
                .path("allTasks")
                .matchesJson(expected);
    }

    private Task createTask(String title) {
        Task task = Task.createTestTask(counter++);
        task.setTitle(title);
        task.setDescription("Test task");
        task.setCategory(new Category());
        task.setTag(new Tag());
        return task;
    }
}
