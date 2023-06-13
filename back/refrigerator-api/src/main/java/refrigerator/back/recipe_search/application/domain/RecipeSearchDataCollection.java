package refrigerator.back.recipe_search.application.domain;

import refrigerator.back.recipe_search.adapter.RecipeSearchDataMapper;
import refrigerator.back.recipe_search.adapter.in.dto.InRecipeSearchDto;
import refrigerator.back.recipe_search.adapter.out.dto.OutRecipeDto;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeSearchDataCollection {
    private final List<OutRecipeDto> data;

    public RecipeSearchDataCollection(List<OutRecipeDto> data) {
        this.data = data;
    }

    public List<InRecipeSearchDto> mappingToInRecipeSearchDto(RecipeSearchDataMapper mapper){
        return data.stream()
                .map(mapper::toInRecipeSearchDto)
                .collect(Collectors.toList());
    }

    public boolean isValid(){
        return data.stream().allMatch(dto -> dto != OutRecipeDto.builder().build());
    }

}
