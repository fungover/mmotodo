package org.fungover.mmotodo.exception;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(int userId) {
        super("User not found with ID: " + userId);
    }
}
