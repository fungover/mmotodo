package org.fungover.mmotodo.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fungover.mmotodo.category.Category;
import org.fungover.mmotodo.category.CategoryService;
import org.fungover.mmotodo.tag.Tag;
import org.fungover.mmotodo.tag.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@GraphQlTest
@Import(value = {GraphQlConfig.class})
class TaskControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private TaskService taskService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private TagService tagService;

    @MockBean
    private Flux<TaskEvent> taskEvent;

    @Autowired
    private ObjectMapper objectMapper;

    private static int counter;
    private List<Task> tasks;
    private Map<Task, Tag> tagsForTasks;
    private Map<Task, Category> catsForTasks;

    @BeforeEach
    void setUp() {
        counter = 1;
        tasks = new ArrayList<>();
        tasks.add(createTask("Something"));
        tasks.add(createTask("Nice"));

        tagsForTasks = tasks.stream().collect(Collectors.toMap(Function.identity(), Task::getTag));
        catsForTasks = tasks.stream().collect(Collectors.toMap(Function.identity(), Task::getCategory));
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
        Mockito.when(categoryService.categoriesForTasks(List.of(task))).thenReturn(Map.of(task, task.getCategory()));
        Mockito.when(tagService.tagsForTasks(List.of(task))).thenReturn(Map.of(task, task.getTag()));

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
        Mockito.when(categoryService.categoriesForTasks(tasks)).thenReturn(catsForTasks);
        Mockito.when(tagService.tagsForTasks(tasks)).thenReturn(tagsForTasks);

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

    @Test
    void ShouldRespondWithTasksByTagId() throws Exception {
        Mockito.when(taskService.getTasksByTagId(2)).thenReturn(tasks);

        // language=GraphQL
        String query = "query { tasksByTagId(tagId: 2) { tag { id } } }";

        var expectedList = tasks.stream().map(task -> new Object() {
            public final Object tag = new Object() {
                public final int id = task.getTag().getId();
            };
        }).toList();

        String expected = objectMapper.writeValueAsString(expectedList);

        graphQlTester.document(query)
                .execute()
                .path("tasksByTagId")
                .matchesJson(expected);
    }

    @Test
    void ShouldAddNewTask() throws Exception {
        TaskCreateDto taskDto = new TaskCreateDto("new task", "this is the new task", "started");
        Task task = Task.createTestTask(1);
        task.setTitle(taskDto.title());
        task.setDescription(taskDto.description());
        task.setStatus(taskDto.status());

        Mockito.when(taskService.addTask(taskDto)).thenReturn(task);

        // language=GraphQL
        String query = """
                    mutation {
                         addTask(task: {
                            title: "new task",
                            description: "this is the new task",
                            status: "started"
                        }) {
                            title
                            description
                            status
                        }
                    }
                """;

        String expected = objectMapper.writeValueAsString(taskDto);

        graphQlTester.document(query)
                .execute()
                .path("addTask")
                .matchesJson(expected);
    }

    @Test
    void ShouldHandleSuccessDeleteTaskResponse() throws Exception {
        Mockito.when(taskService.deleteTask(1)).thenReturn(true);

        // language=GraphQL
        String query = "mutation { deleteTask(id: 1) }";

        String expected = objectMapper.writeValueAsString("Task with id: 1 is deleted");

        graphQlTester.document(query)
                .execute()
                .path("deleteTask")
                .matchesJson(expected);
    }

    @Test
    void ShouldHandleFailedDeleteTaskResponse() throws Exception {
        Mockito.when(taskService.deleteTask(1)).thenReturn(false);

        // language=GraphQL
        String query = "mutation { deleteTask(id: 1) }";

        String expected = objectMapper.writeValueAsString("Could not delete task with id 1");

        graphQlTester.document(query)
                .execute()
                .path("deleteTask")
                .matchesJson(expected);
    }

    @Test
    void ShouldUpdateTaskTitle() throws Exception {
        TaskUpdateDto updateDto = new TaskUpdateDto(1, "Updated Task", null, null, null, null);
        Task task = Task.createTestTask(1);
        task.setTitle(updateDto.title());
        task.setDescription(updateDto.description());
        task.setStatus(updateDto.status());

        Mockito.when(taskService.updateTask(updateDto)).thenReturn(task);

        // language=GraphQL
        String query = "mutation { updateTask(task: { id: 1, title: \"Updated Task\"}) { title }}";

        String expected = objectMapper.writeValueAsString(new Object() {
            public final String title = "Updated Task";
        });

        graphQlTester.document(query)
                .execute()
                .path("updateTask")
                .matchesJson(expected);

    }

    private Task createTask(String title) {
        Task task = Task.createTestTask(counter);
        task.setTitle(title);
        task.setDescription("Test task");
        Category category = new Category();
        category.setId(counter);
        task.setCategory(category);
        Tag tag = new Tag();
        tag.setId(counter);
        task.setTag(tag);
        counter++;
        return task;
    }
}
