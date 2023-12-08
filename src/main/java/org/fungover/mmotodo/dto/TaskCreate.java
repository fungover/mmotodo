package org.fungover.mmotodo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TaskCreate(
        @NotNull @NotEmpty
        String title,
        String description,
        String status
    )
{
}