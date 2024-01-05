package org.fungover.mmotodo.category;

import org.fungover.mmotodo.task.Task;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> category(List<Integer> taskIds) {
        return categoryRepository.findAllById(taskIds);
    }

    public Map<Task, Category> categoriesForTasks(List<Task> tasks) {
        var categories =categoryRepository.findAll();
        return tasks.stream()
                .collect(
                        Collectors.toMap(
                                task -> task    ,
                                task ->
                                        categories.stream()
                                                .filter(c -> c.getId().equals(task.getCategory().getId()))
                                                .findFirst()
                                                .orElse(null)));
    }
}


