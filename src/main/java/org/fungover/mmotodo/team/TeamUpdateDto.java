package org.fungover.mmotodo.team;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

public record TeamUpdateDto(int id, @NotNull @NotEmpty String name) implements Serializable {
}
