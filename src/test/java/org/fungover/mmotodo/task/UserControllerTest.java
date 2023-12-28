package org.fungover.mmotodo.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.fungover.mmotodo.user.User;
import org.fungover.mmotodo.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

/*
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
    private User user;

    @BeforeEach
    void setUp(){
        counter = 1;
        user = new User();
        user.setId(1);
        user.setFirstName("Anton");
        user.setLastName("Holst");
        user.setRole("ADMIN");
    }


    @Test
    void ShouldReturnSelectedUser() throws Exception{
        Mockito.when(userService.getUserById(1)).thenReturn(user);

    }
}
*/

