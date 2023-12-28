package org.fungover.mmotodo.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private final UserService userService;


    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @QueryMapping
    public User userById(@Argument int id){
        return userService.getUserById(id);
    }

    @MutationMapping
    public User createUser(@Valid @Argument UserDto user){
        return userService.addUser(user);
    }





}
