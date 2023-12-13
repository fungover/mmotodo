package org.fungover.mmotodo;

import org.fungover.mmotodo.entities.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
        List<Task> findByUserId(Long userId);
        List<Task> findByTeamId(Long teamId);
}
