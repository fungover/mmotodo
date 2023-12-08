package org.fungover.mmotodo.task;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.fungover.mmotodo.category.Category;
import org.fungover.mmotodo.tag.Tag;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @Column(name = "created")
    @CreationTimestamp
    private Instant created;

    @Column(name = "updated")
    @UpdateTimestamp
    private Instant updated;

    @NotNull
    @Column(name = "time_estimation", nullable = false)
    private Double timeEstimation;

    @NotNull
    @Column(name = "due_date", nullable = false)
    private Instant dueDate;

    @Lob
    @Column(name = "status")
    private String status;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Task task = (Task) o;
        return getId() != null && Objects.equals(getId(), task.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public static Task of(TaskUpdate taskUpdate) {
        Task task = new Task();
        if (taskUpdate.title() != null) task.setTitle(taskUpdate.title());
        if (taskUpdate.description() != null) task.setDescription(taskUpdate.description());
        if (taskUpdate.status() != null) task.setStatus(taskUpdate.status());

        Tag tag = new Tag();
        tag.setId(taskUpdate.tagId());
        task.setTag(tag);

        Category category = new Category();
        category.setId(taskUpdate.categoryId());
        task.setCategory(category);

        return task;
    }
}