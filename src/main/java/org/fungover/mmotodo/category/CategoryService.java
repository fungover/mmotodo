package org.fungover.mmotodo.category;

import org.fungover.mmotodo.task.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Map<Task, Category> categoriesForTasks(List<Task> tasks) {

        var categoryIds = tasks.stream().map(task -> task.getCategory().getId()).collect(Collectors.toList());
        var categories = categoryRepository.findByIdIn(categoryIds);

        return tasks.stream()
                .collect(
                        Collectors.toMap(
                                task -> task    ,
                                task ->
                                        categories.stream()
                                                .filter(c -> c.getId().equals(task.getCategory().getId()))
                                                .findFirst()
                                                .orElse(new Category())));
    }
}
