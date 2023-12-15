package org.fungover.mmotodo.exception;

public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException() {
        super("Category not found");
    }
}
