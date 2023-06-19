package refrigerator.server.api.recipe_search.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.image.ImageGenerator;
import refrigerator.server.api.recipe_search.dto.InRecipeBasicListDTO;
import refrigerator.back.recipe_search.application.dto.InRecipeSearchDto;
import refrigerator.back.recipe_search.application.port.in.SearchRecipeUseCase;
import refrigerator.back.recipe_search.exception.RecipeSearchExceptionType;
import refrigerator.back.recipe_searchword.application.port.in.AddSearchWordUseCase;
import refrigerator.server.api.recipe_search.dto.InRecipeSearchConditionDto;
import refrigerator.server.api.recipe_search.mapper.InRecipeSearchMapper;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class RecipeSearchController {

    private final SearchRecipeUseCase searchRecipeUseCase;
    private final AddSearchWordUseCase addSearchWordUseCase;
    private final ImageGenerator recipeImageGenerator;
    private final InRecipeSearchMapper mapper;
    private final GetMemberEmailUseCase memberInformation;


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
        List<InRecipeSearchDto> data = searchRecipeUseCase.search(mapper.toRecipeSearchCondition(requestDto), page, size);
        data.forEach(recipe -> {
            if (recipe.checkNotNull()){
                recipe.generateImageUrl(recipeImageGenerator);
            } else{
                throw new BusinessException(RecipeSearchExceptionType.INVALID_RECIPE_DATA);
            }
        });
        addSearchWordUseCase.addSearchWord(memberInformation.getMemberEmail(), requestDto.getSearchWord());
        return new InRecipeBasicListDTO<>(data);
    }


}
