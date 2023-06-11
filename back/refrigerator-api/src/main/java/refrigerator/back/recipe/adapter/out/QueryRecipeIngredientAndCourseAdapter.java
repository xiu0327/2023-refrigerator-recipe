package refrigerator.back.recipe.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.out.dto.OutRecipeOtherDto;
import refrigerator.back.recipe.adapter.out.repository.RecipeSelectQueryRepository;
import refrigerator.back.recipe.application.domain.RecipeIngredientAndCourseCollection;
import refrigerator.back.recipe.application.port.out.GetRecipeIngredientAndCourseDataPort;

@Repository
@RequiredArgsConstructor
public class QueryRecipeIngredientAndCourseAdapter implements GetRecipeIngredientAndCourseDataPort {

    private final RecipeSelectQueryRepository repository;

    @Override
    public RecipeIngredientAndCourseCollection getData(Long recipeId) {
        OutRecipeOtherDto data = repository.selectRecipeIngredientAndCourse(recipeId);
        return new RecipeIngredientAndCourseCollection(data.getIngredients(), data.getCourses());
    }
}
