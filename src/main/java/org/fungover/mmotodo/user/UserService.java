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
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public User addUser(UserDto userDto) {
        User user = new User();

        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setRole(userDto.role());

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(UserDto userDto) {
        User updatedUser = userRepository.findById(userDto.id()).orElseThrow(UserNotFoundException::new);

        updatedUser.setId(userDto.id());
        updatedUser.setFirstName(userDto.firstName());
        updatedUser.setLastName(userDto.lastName());
        updatedUser.setRole(userDto.role());

        return userRepository.save(updatedUser);
    }

}
