package refrigerator.back.recipe.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.out.dto.RecipeMappingDTO;
import refrigerator.back.recipe.adapter.out.dto.RecipeSearchConditionDTO;
import refrigerator.back.recipe.adapter.out.entity.RecipeCategory;
import refrigerator.back.recipe.adapter.out.entity.RecipeFoodType;
import refrigerator.back.recipe.adapter.out.mapper.RecipeMapper;
import refrigerator.back.recipe.adapter.out.repository.RecipeQueryRepository;
import refrigerator.back.recipe.adapter.out.repository.RecipeSearchQueryRepository;
import refrigerator.back.recipe.application.domain.entity.RecipeCourseDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeIngredientDomain;
import refrigerator.back.recipe.application.port.out.ReadRecipePort;
import refrigerator.back.recipe.application.port.out.SearchRecipePort;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecipeLookUpAdapter implements ReadRecipePort, SearchRecipePort {

    private final RecipeQueryRepository recipeQueryRepository;
    private final RecipeSearchQueryRepository recipeSearchQueryRepository;
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

    @Override
    public List<RecipeDomain> search(String recipeType,
                                     String foodType,
                                     String difficulty,
                                     Double score,
                                     int page,
                                     int size) {
        RecipeSearchConditionDTO dto = RecipeSearchConditionDTO.builder()
                .recipeType(recipeType)
                .recipeFoodType(foodType)
                .difficulty(difficulty)
                .score(score).build();
        return recipeSearchQueryRepository.searchRecipe(dto, PageRequest.of(page, 11))
                .stream().map(recipeMapper::listDtoToRecipeDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findRecipeFoodTypeCond() {
        return recipeSearchQueryRepository.findRecipeFoodTypeList()
                .stream().map(RecipeFoodType::getTypeName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findRecipeCategoryCond() {
        return recipeSearchQueryRepository.findRecipeCategoryList()
                .stream().map(RecipeCategory::getCategoryName)
                .collect(Collectors.toList());
    }
}
