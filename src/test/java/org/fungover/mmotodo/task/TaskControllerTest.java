package org.fungover.mmotodo.task;

import org.fungover.mmotodo.category.Category;
import org.fungover.mmotodo.tag.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureGraphQlTester
@ActiveProfiles("dev")
class TaskControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private TaskService taskService;

    private final List<Task> tasks = new ArrayList<>();

    private static int counter = 1;

    @BeforeEach
    void setUp() {
        tasks.add(createTask("Something"));
        tasks.add(createTask("Nice"));
        Mockito.when(taskService.getAllTasks()).thenReturn(tasks);
    }

    @Test
    void ShouldRespondWithAllTasks() throws Exception {
        // language=GraphQL
        String query = "query { allTasks { title } }";

        graphQlTester.document(query)
                .execute()
                .path("allTasks")
                .entityList(Task.class)
                .hasSize(2);
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