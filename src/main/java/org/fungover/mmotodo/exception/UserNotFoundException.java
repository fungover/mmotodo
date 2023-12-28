package org.fungover.mmotodo.exception;

public class UserNotFoundException extends ResourceNotFoundException{
    public UserNotFoundException() {
        super("User not found");
    }
}
