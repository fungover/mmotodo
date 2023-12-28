package org.fungover.mmotodo.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.fungover.mmotodo.task.Task;

public record UserDto(
        Integer id,
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        @NotNull
        String role
        ) {
}
