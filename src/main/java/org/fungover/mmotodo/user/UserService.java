package org.fungover.mmotodo.user;

import jakarta.transaction.Transactional;
import org.fungover.mmotodo.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(id));
    }

    @Transactional
    public User addUser(UserCreateDto userCreateDto) {
        User user = new User();

        user.setFirstName(userCreateDto.firstName());
        user.setLastName(userCreateDto.lastName());
        user.setRole(userCreateDto.role());

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(UserDto userDto) {
        User updatedUser = userRepository.findById(userDto.id()).orElseThrow(() ->
                new UserNotFoundException(userDto.id()));

        updatedUser.setId(userDto.id());
        updatedUser.setFirstName(userDto.firstName());
        updatedUser.setLastName(userDto.lastName());
        updatedUser.setRole(userDto.role());

        return userRepository.save(updatedUser);
    }

    @Transactional
    public String deleteUser(int id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(id));
        userRepository.deleteById(user.getId());

        return "User with id: " + id + " was successfully deleted";
    }

}
