package org.fungover.mmotodo.task;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.boot.context.properties.bind.DefaultValue;

public record TaskUpdateDto(
        @NotNull @PositiveOrZero
        int id,
        String title,
        String description,
        String status,
        Integer categoryId,
        Integer tagId
) {
}