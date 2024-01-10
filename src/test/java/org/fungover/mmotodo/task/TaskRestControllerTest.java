package org.fungover.mmotodo.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fungover.mmotodo.exception.TaskNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TaskRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskService service;

    @InjectMocks
    private TaskRestController taskRestController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(taskRestController).build();
    }
    @Test
    void getAllTasks() throws Exception {

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createTask() throws Exception {

        TaskCreateDto taskCreateDto = new TaskCreateDto("Test task", "Test description", "Open");

        //Mock behaviour
        Task mockTask = new Task();
        mockTask.setTitle(taskCreateDto.title());
        mockTask.setDescription(taskCreateDto.description());
        mockTask.setStatus(taskCreateDto.status());

        given(service.addTask(any(TaskCreateDto.class))).willReturn(mockTask);

        // Perform test
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateTask() throws Exception {
        int taskId = 1;
        TaskUpdateDto updateDto = new TaskUpdateDto(taskId,"Updated title", "Updated description", "InProgress", 2, 3);

        Task mockTask = Task.createTestTask(taskId);

        //Mock behaviour
        mockTask.setTitle(updateDto.title());
        mockTask.setDescription(updateDto.description());
        mockTask.setStatus(updateDto.status());

        given(service.updateTask(any(TaskUpdateDto.class))).willReturn(mockTask);

        //Perform Test
        mockMvc.perform(put("/api/tasks/" + taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getTaskByIdShouldReturnTaskWithId() throws Exception {
        int taskId = 1;
        Task task = Task.createTestTask(taskId);

        given(service.getTaskById(taskId)).willReturn(task);

        mockMvc.perform(get("/api/tasks/" + taskId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteTask_NoContent() throws Exception {
        int taskId = 1;

        given(service.deleteTask(1)).willReturn(true);

        mockMvc.perform(delete("/api/tasks/" + taskId ))
                .andExpect(status().isNoContent());
    }
    @Test
    void deleteTask_NotFound() throws Exception {
        int taskId = 1;

        given(service.deleteTask(1)).willReturn(false);

        mockMvc.perform(delete("/api/tasks/" + taskId ))
                .andExpect(status().isNotFound());
    }
}