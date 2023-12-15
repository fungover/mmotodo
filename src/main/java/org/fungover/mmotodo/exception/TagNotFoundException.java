package org.fungover.mmotodo.exception;

public class TagNotFoundException extends ResourceNotFoundException {
    public TagNotFoundException() {
        super("Tag not found");
    }
}
