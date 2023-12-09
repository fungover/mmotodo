package org.fungover.mmotodo.entities.team;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TeamCreate(@NotNull @NotEmpty String name,
                         String role,
                         String category) {

}
