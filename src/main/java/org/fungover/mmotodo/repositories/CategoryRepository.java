package org.fungover.mmotodo.repositories;

import org.fungover.mmotodo.entities.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
