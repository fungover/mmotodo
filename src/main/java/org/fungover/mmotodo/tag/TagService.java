package org.fungover.mmotodo.tag;

import org.fungover.mmotodo.task.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Map<Task, Tag> tagsForTasks(List<Task> tasks) {

        var tagIds = tasks.stream().map(task -> task.getTag().getId()).collect(Collectors.toList());
        var tags = tagRepository.findByIdIn(tagIds);

        return tasks.stream()
                .collect(
                        Collectors.toMap(
                                task -> task,
                                task ->
                                        tags.stream()
                                                .filter(t -> t.getId().equals(task.getTag().getId()))
                                                .findFirst()
                                                .orElse(new Tag())));
    }
}
