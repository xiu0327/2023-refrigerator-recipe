package refrigerator.back.recipe.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe.adapter.in.dto.InRecipeCourseDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeBasicListDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDetailDTO;
import refrigerator.back.recipe.adapter.mapper.RecipeDtoMapper;
import refrigerator.back.recipe.application.domain.entity.Recipe;
import refrigerator.back.recipe.application.port.in.FindRecipeCourseUseCase;
import refrigerator.back.recipe.application.port.in.FindRecipeListUseCase;
import refrigerator.back.recipe.application.port.out.ReadRecipePort;
import refrigerator.back.recipe.application.port.in.FindRecipeDetailUseCase;
import refrigerator.back.recipe.application.port.out.AddRecipeViewsPort;
import refrigerator.back.recipe.exception.RecipeExceptionType;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeService implements FindRecipeListUseCase, FindRecipeDetailUseCase, FindRecipeCourseUseCase {

    private final ReadRecipePort recipeReadPort;
    private final AddRecipeViewsPort addRecipeViewsPort;
    private final RecipeDtoMapper mapper;
    private final RecipeFormatService recipeFormatService;

    @Override
    @Transactional
    public InRecipeDetailDTO getRecipe(Long recipeID, boolean isViewed) {
        try{
            Recipe recipe = recipeReadPort.getRecipeDetails(recipeID);
            if (!isViewed){
                addRecipeViewsPort.addViews(recipeID);
            }
            return transRecipeDto(recipe);
        }catch (RuntimeException e){
            log.info(e.getMessage());
            throw new BusinessException(RecipeExceptionType.NOT_FOUND_RECIPE);
        }
    }

    private InRecipeDetailDTO transRecipeDto(Recipe recipe) {
        InRecipeDetailDTO dto = mapper.toInRecipeDetailsDto(
                recipe,
                recipe.getDetails(),
                recipe.getDetails().getScore().calculateScore());
        dto.setIngredients(recipe.getIngredients()
                .stream().map(mapper::toInRecipeIngredientDto)
                .collect(Collectors.toSet()));
        dto.settingFormat(
                recipeFormatService.changeServingsFormat(recipe.getServings()),
                recipeFormatService.changeKcalFormat(recipe.getKcal()),
                recipeFormatService.changeCookingTimeFormat(recipe.getCookingTime())
        );
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public InRecipeBasicListDTO<InRecipeDTO> getRecipeList(int page, int size) {
        return new InRecipeBasicListDTO<>(
                recipeReadPort.getRecipeList(page, size));
    }

    @Override
    @Transactional(readOnly = true)
    public InRecipeBasicListDTO<InRecipeCourseDTO> getRecipeCourse(Long recipeID) {
        List<InRecipeCourseDTO> courses = recipeReadPort.getRecipeCourse(recipeID)
                .stream().map(mapper::toInRecipeCourseDto)
                .collect(Collectors.toList());
        return new InRecipeBasicListDTO<>(courses);
    }
}
