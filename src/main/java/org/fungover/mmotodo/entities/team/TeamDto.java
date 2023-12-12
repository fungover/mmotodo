package org.fungover.mmotodo.entities.team;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TeamDto(int id, @NotNull @NotEmpty String name,
                      List<Integer> userIds,
                      List <Integer> taskIds) {



}
