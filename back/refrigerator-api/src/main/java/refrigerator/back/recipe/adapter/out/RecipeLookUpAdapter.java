package refrigerator.back.recipe.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import refrigerator.back.recipe.adapter.in.dto.InRecipeDTO;
import refrigerator.back.recipe.adapter.mapper.RecipeDtoMapper;
import refrigerator.back.recipe.adapter.out.repository.query.RecipeQueryRepository;
import refrigerator.back.recipe.adapter.out.repository.query.RecipeSearchQueryRepository;
import refrigerator.back.recipe.application.domain.entity.*;
import refrigerator.back.recipe.application.port.out.ReadRecipePort;
import refrigerator.back.recipe.application.port.out.SearchRecipePort;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecipeLookUpAdapter implements ReadRecipePort, SearchRecipePort {

    private final RecipeQueryRepository recipeQueryRepository;
    private final RecipeSearchQueryRepository recipeSearchQueryRepository;
    private final RecipeDtoMapper mapper;

    @Override
    public Recipe getRecipeDetails(Long recipeID) {
        Recipe recipe = recipeQueryRepository.findRecipeById(recipeID);
        recipe.initDetails(mapper.toRecipeDetails(
                recipeQueryRepository.findRecipeDetails(recipeID)
        ));
        return recipe;
    }

    @Override
    public List<RecipeCourse> getRecipeCourse(Long recipeID) {
        return recipeQueryRepository.findRecipeCourse(recipeID);
    }

    @Override
    public List<InRecipeDTO> getRecipeList(int page, int size) {
        return recipeQueryRepository.findRecipeList(PageRequest.of(page, size))
                .stream().map(recipe -> mapper.toInRecipeDto(
                                recipe,
                                recipe.getScore().calculateScore()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InRecipeDTO> search(RecipeSearchCondition condition, int page, int size) {
        return recipeSearchQueryRepository.searchRecipe(condition, PageRequest.of(page, 11))
                .stream().map(recipe -> mapper.toInRecipeDto(
                        recipe,
                        recipe.getScore().calculateScore()))
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
