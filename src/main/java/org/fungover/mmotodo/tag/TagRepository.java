package org.fungover.mmotodo.tag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> findByIdIn(List<Integer> taskIdList);
}
