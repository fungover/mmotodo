package org.fungover.mmotodo.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.apache.tomcat.util.security.Escape;
import org.fungover.mmotodo.exception.UserNotFoundException;
import org.fungover.mmotodo.user.User;
import org.fungover.mmotodo.user.UserCreateDto;
import org.fungover.mmotodo.user.UserDto;
import org.fungover.mmotodo.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.GraphQlResponse;
import org.springframework.graphql.test.tester.GraphQlTester;


@GraphQlTest
@Import(value = {GraphQlConfig.class})
public class UserControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private static int counter;
    private User userStartup;

    @BeforeEach
    void setUp() {
        counter = 1;
        userStartup = new User();
        userStartup.setId(1);
        userStartup.setFirstName("John");
        userStartup.setLastName("Doe");
        userStartup.setRole("ADMIN");
    }


    @Test
    void ShouldReturnWithUserById() throws Exception {
        Mockito.when(userService.getUserById(1)).thenReturn(userStartup);

        // language=GraphQL
        String query = "query { getUserById(id: 1) { id, firstName, lastName, role, created, updated, tasks { id }}}";

        var expectedUser = userStartup;
        String expected = objectMapper.writeValueAsString(expectedUser);
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(expected);

        graphQlTester.document(query)
                .execute()
                .path("getUserById")
                .matchesJson(json.toJSONString());
    }

    @Test
    void ShouldAddNewUser() throws Exception {
        UserCreateDto userCreateDto = new UserCreateDto("John", "Doe", "ADMIN");
        Mockito.when(userService.addUser(userCreateDto)).thenReturn(userStartup);

        // language=GraphQL
        String query = """
                    mutation {
                        addUser(user: {
                            firstName: "John",
                            lastName: "Doe",
                            role: "ADMIN"
                        }) {
                            firstName
                            lastName
                            role
                        }
                    }
                """;

        String expected = objectMapper.writeValueAsString(userCreateDto);

        graphQlTester.document(query)
                .execute()
                .path("addUser")
                .matchesJson(expected);
    }

    @Test
    void ShouldUpdateUser() throws Exception{
        UserDto userDto = new UserDto(1, "John", "Deer", "ADMIN");
        userStartup.setLastName("Deer");

        Mockito.when(userService.updateUser(userDto)).thenReturn(userStartup);

        // language=GraphQL
        String query = """
                mutation {
                    updateUser(user: {
                        id: 1,
                        firstName: "John",
                        lastName: "Deer",
                        role: "ADMIN"
                    }) {
                        id
                        firstName
                        lastName
                        role
                    }
                }

                """;

        String expected = objectMapper.writeValueAsString(userDto);

        graphQlTester.document(query)
                .execute()
                .path("updateUser")
                .matchesJson(expected);
    }

    @Test
    void ShouldDeleteUser() throws Exception {
        Mockito.when(userService.deleteUser(1)).thenReturn("User 1 deleted");

        // language=GraphQL
        String query = """
                mutation {
                    deleteUser(id: 1)
                }
                """;

        String expected = objectMapper.writeValueAsString("User 1 deleted");

        graphQlTester.document(query)
                .execute()
                .path("deleteUser")
                .matchesJson(expected);
    }

}


