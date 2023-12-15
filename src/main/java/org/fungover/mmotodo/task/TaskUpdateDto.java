package org.fungover.mmotodo.task;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TaskUpdateDto(
        @NotNull @PositiveOrZero
        Integer id,
        String title,
        String description,
        String status,
        Integer categoryId,
        Integer tagId
) {
}