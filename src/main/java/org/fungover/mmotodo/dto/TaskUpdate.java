package org.fungover.mmotodo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TaskUpdate(
        @NotNull @PositiveOrZero
        int id,
        @NotNull @NotEmpty
        String title,
        String description,
        String status,
        int categoryId,
        int tagId
) {
}
