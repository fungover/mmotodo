package org.fungover.mmotodo.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fungover.mmotodo.category.Category;
import org.fungover.mmotodo.tag.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.io.Writer;
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

    private static int counter = 1;

    @Test
    void ShouldRespondWithAllTaskTitles() throws Exception {
        List<Task> tasks = new ArrayList<>();
        tasks.add(createTask("Something"));
        tasks.add(createTask("Nice"));

        Mockito.when(taskService.getAllTasks()).thenReturn(tasks);

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

    @Test
    void shouldRespondWithTaskData() throws Exception {
        Task task = createTask("Task");
        Mockito.when(taskService.getTaskById(1)).thenReturn(task);

        // language=GraphQL
        String query = """
          query MyQuery {
          taskById(id: 1) {
            id
            title
            description
            created
            updated
            timeEstimation
            dueDate
            status
            tag {
              id
              name
              description
              created
              updated
            }
            category {
              id
              name
              description
              created
              updated
            }
          }
        }
        """;

        String expected = objectMapper.writeValueAsString(task);

        graphQlTester.document(query)
                .execute()
                .path("taskById")
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
