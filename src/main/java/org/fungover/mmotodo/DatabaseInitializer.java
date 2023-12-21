package org.fungover.mmotodo;

import org.fungover.mmotodo.category.Category;
import org.fungover.mmotodo.category.CategoryRepository;
import org.fungover.mmotodo.tag.Tag;
import org.fungover.mmotodo.tag.TagRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements ApplicationRunner {

    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;

    public DatabaseInitializer(TagRepository tagRepository, CategoryRepository categoryRepository) {
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (tagRepository.count() == 0) {
            Tag tag1 = new Tag();
            tag1.setName("Design");
            tag1.setDescription("Paint a pretty picture");

            Tag tag2 = new Tag();
            tag2.setName("Documentation");
            tag2.setDescription("Let's jot some stuff down");

            Tag tag3 = new Tag();
            tag3.setName("Enhancement");
            tag3.setDescription("Ways to make it better");

            Tag tag4 = new Tag();
            tag4.setName("Performance");
            tag4.setDescription("Make it run smoother");

            tagRepository.save(tag1);
            tagRepository.save(tag2);
            tagRepository.save(tag3);
            tagRepository.save(tag4);
        }

        if (categoryRepository.count() == 0) {
            Category category1 = new Category();
            category1.setName("Example");
            category1.setDescription("This is an example category");

            categoryRepository.save(category1);
        }
    }
}
