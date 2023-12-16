package org.fungover.mmotodo.team;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TeamDto(int id, String name, List<Integer> users, List<Integer> tasks
               ) {



}
