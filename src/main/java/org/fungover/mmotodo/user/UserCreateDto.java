package org.fungover.mmotodo.user;

import jakarta.validation.constraints.NotNull;

public record UserCreateDto(@NotNull
                            String firstName,
                            @NotNull
                            String lastName,
                            @NotNull
                            String role) {

}
