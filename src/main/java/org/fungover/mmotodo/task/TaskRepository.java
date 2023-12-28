package org.fungover.mmotodo.task;

import org.fungover.mmotodo.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByCategoryId(int categoryId);
    List<Task> findAllByTagId(int tagId);

}