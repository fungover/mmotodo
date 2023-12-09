package org.fungover.mmotodo.entities.team;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.fungover.mmotodo.entities.task.Task;
import org.fungover.mmotodo.entities.user.User;

import java.util.List;

public record TeamUpdate(int id, @NotNull @NotEmpty String name,
                         List<Integer> userIds,
                         List <Integer> taskIds) {
}
