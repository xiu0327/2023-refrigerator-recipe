package refrigerator.back.recipe.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.out.dto.RecipeMappingDTO;
import refrigerator.back.recipe.adapter.out.mapper.RecipeMapper;
import refrigerator.back.recipe.adapter.out.repository.RecipeQueryRepository;
import refrigerator.back.recipe.application.domain.entity.RecipeCourseDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeIngredientDomain;
import refrigerator.back.recipe.application.port.out.ReadRecipePort;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecipeLookUpAdapter implements ReadRecipePort {

    private final RecipeQueryRepository recipeQueryRepository;
    private final RecipeMapper recipeMapper;

    @Override
    public RecipeDomain getRecipeDetails(Long recipeID) {
        RecipeMappingDTO recipe = recipeQueryRepository.findRecipeByID(recipeID);
        RecipeDomain recipeDomain = recipeMapper.toRecipeDomain(recipe);
        Set<RecipeIngredientDomain> ingredients = recipe.getIngredients().stream()
                .map(recipeMapper::toIngredientDomain)
                .collect(Collectors.toSet());
        recipeDomain.initIngredient(ingredients);
        return recipeDomain;
    }

    @Override
    public List<RecipeCourseDomain> getRecipeCourse(Long recipeID) {
        return recipeQueryRepository.findRecipeCourse(recipeID)
                .stream().map(recipeMapper::toCourseDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeDomain> getRecipeList(int page, int size) {
        return recipeQueryRepository.findRecipeList(PageRequest.of(page, size))
                .stream().map(recipeMapper::listDtoToRecipeDomain)
                .collect(Collectors.toList());
    }
}
