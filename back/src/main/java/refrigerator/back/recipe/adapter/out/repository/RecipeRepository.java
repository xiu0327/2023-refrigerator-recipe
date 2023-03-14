package refrigerator.back.recipe.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends RecipeQuerydslRepository {
    void save(Object entity);
}
