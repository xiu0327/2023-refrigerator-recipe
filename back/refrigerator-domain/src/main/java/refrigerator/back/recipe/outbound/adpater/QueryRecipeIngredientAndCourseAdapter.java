package refrigerator.back.recipe.outbound.adpater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.recipe.outbound.dto.OutRecipeOtherDto;
import refrigerator.back.recipe.outbound.repository.query.RecipeSelectQueryRepository;
import refrigerator.back.recipe.application.domain.RecipeIngredientAndCourseCollection;
import refrigerator.back.recipe.application.port.out.GetRecipeIngredientAndCourseDataPort;

@Component
@RequiredArgsConstructor
public class QueryRecipeIngredientAndCourseAdapter implements GetRecipeIngredientAndCourseDataPort {

    private final RecipeSelectQueryRepository repository;

    @Override
    public RecipeIngredientAndCourseCollection getData(Long recipeId) {
        OutRecipeOtherDto data = repository.selectRecipeIngredientAndCourse(recipeId);
        return new RecipeIngredientAndCourseCollection(data.getIngredients(), data.getCourses());
    }
}
