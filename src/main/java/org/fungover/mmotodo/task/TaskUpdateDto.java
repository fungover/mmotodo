package org.fungover.mmotodo.task;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.boot.context.properties.bind.DefaultValue;

public record TaskUpdateDto(
        @NotNull @PositiveOrZero
        int id,
        @NotNull @DefaultValue("")
        String title,
        @NotNull @DefaultValue("")
        String description,
        @NotNull @DefaultValue("started")
        String status,
        @DefaultValue("1")
        int categoryId,
        @DefaultValue("1")
        int tagId
) {
}