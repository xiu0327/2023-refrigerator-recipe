package refrigerator.back.recipe.adapter.out.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.recipe.adapter.out.entity.Recipe;
import refrigerator.back.recipe.adapter.out.entity.RecipeCourse;
import refrigerator.back.recipe.adapter.out.entity.RecipeSearch;

import java.util.List;

public interface RecipeRepository {
    Recipe findRecipeByID(Long recipeID);
    List<RecipeCourse> findRecipeCourse(Long recipeID);
    List<Recipe> findRecipeList(Pageable pageable);
    List<Recipe> searchRecipe(RecipeSearch recipeSearch);
    void updateRecipeViews(Long recipeID);
    void updateRecipeScore(Long recipeID, double score);
    void save(Object entity);
}
