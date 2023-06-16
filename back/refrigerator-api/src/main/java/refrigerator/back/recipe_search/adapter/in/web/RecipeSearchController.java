package refrigerator.back.recipe_search.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.global.common.api.MemberInformation;
import refrigerator.back.global.exception.domain.BusinessException;
import refrigerator.back.global.image.ImageGenerator;
import refrigerator.back.recipe.adapter.in.dto.InRecipeBasicListDTO;
import refrigerator.back.recipe_search.adapter.RecipeSearchDataMapper;
import refrigerator.back.recipe_search.adapter.in.dto.InRecipeSearchConditionDto;
import refrigerator.back.recipe_search.adapter.in.dto.InRecipeSearchDto;
import refrigerator.back.recipe_search.application.port.in.SearchRecipeUseCase;
import refrigerator.back.recipe_search.exception.RecipeSearchExceptionType;
import refrigerator.back.recipe_searchword.application.port.in.AddSearchWordUseCase;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class RecipeSearchController {

    private final SearchRecipeUseCase searchRecipeUseCase;
    private final AddSearchWordUseCase addSearchWordUseCase;
    private final ImageGenerator recipeImageGenerator;
    private final RecipeSearchDataMapper mapper;


    @PostMapping("/api/recipe/search")
    public InRecipeBasicListDTO<InRecipeSearchDto> getRecipeSearchList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "11") int size,
            @RequestBody InRecipeSearchConditionDto requestDto){
        return search(page, size, requestDto);
    }

    @GetMapping("/api/recipe/search/normal")
    public InRecipeBasicListDTO<InRecipeSearchDto> getRecipeNormalList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "11") int size){
        return normalSearch(page, size);
    }

    private InRecipeBasicListDTO<InRecipeSearchDto> normalSearch(int page, int size) {
        List<InRecipeSearchDto> data = searchRecipeUseCase.normalSearch(page, size);
        return new InRecipeBasicListDTO<>(data);
    }

    private InRecipeBasicListDTO<InRecipeSearchDto> search(int page, int size, InRecipeSearchConditionDto requestDto) {
        List<InRecipeSearchDto> data = searchRecipeUseCase.search(
                mapper.toRecipeSearchCondition(requestDto),
                page,
                size);
        data.forEach(recipe -> {
            if (recipe.isNotNull()){
                recipe.generateImageUrl(recipeImageGenerator);
            } else{
                throw new BusinessException(RecipeSearchExceptionType.INVALID_RECIPE_DATA);
            }
        });
        addSearchWordUseCase.addSearchWord(MemberInformation.getMemberEmail(), requestDto.getSearchWord());
        return new InRecipeBasicListDTO<>(data);
    }


}
