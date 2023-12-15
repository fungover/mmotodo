package org.fungover.mmotodo.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.fungover.mmotodo.task.Task;

public record UserDto(
        @NotNull
        @PositiveOrZero
        Integer id,
        String firstName,
        String lastName,
        String role,
        Task tasks
        ) {
}
