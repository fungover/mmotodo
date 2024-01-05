package org.fungover.mmotodo.tag;

import org.fungover.mmotodo.task.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository TagRepository) {
        this.tagRepository = TagRepository;
    }

    public Map<Task, Tag> tagsForTasks(List<Task> tasks) {
        var tags = tagRepository.findAll();
        return tasks.stream()
                .collect(
                        Collectors.toMap(
                                task -> task    ,
                                task ->
                                        tags.stream()
                                                .filter(c -> c.getId().equals(task.getTag().getId()))
                                                .findFirst()
                                                .orElse(new Tag())));
    }
}