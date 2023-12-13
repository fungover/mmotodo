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

    private static int counter;
    private List<Task> tasks;

    @BeforeEach
    void setUp() {
        counter = 1;
        tasks = new ArrayList<>();
        tasks.add(createTask("Something"));
        tasks.add(createTask("Nice"));
    }

    @Test
    void ShouldRespondWithAllTaskTitles() throws Exception {
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

    @Test
    void ShouldRespondWithTasksByCategoryId() throws Exception {
        Mockito.when(taskService.getTasksByCategoryId(2)).thenReturn(tasks);

        // language=GraphQL
        String query = "query { tasksByCategoryId(categoryId: 2) { category { id } } }";

        var expectedList = tasks.stream().map(task -> new Object() {
            public final Object category = new Object() {
                public final int id = task.getCategory().getId();
            };
        }).toList();

        String expected = objectMapper.writeValueAsString(expectedList);

        graphQlTester.document(query)
                .execute()
                .path("tasksByCategoryId")
                .matchesJson(expected);
    }

    private Task createTask(String title) {
        Task task = Task.createTestTask(counter);
        task.setTitle(title);
        task.setDescription("Test task");
        Category category = new Category();
        category.setId(counter);
        category.setName("Category");
        task.setCategory(category);
        task.setTag(new Tag());
        counter++;
        return task;
    }
}
