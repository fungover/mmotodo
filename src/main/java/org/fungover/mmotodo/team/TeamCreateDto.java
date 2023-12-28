package org.fungover.mmotodo.team;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

public record TeamCreateDto(@NotNull @NotEmpty String name
) implements Serializable {


}
