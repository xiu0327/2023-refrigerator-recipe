package refrigerator.back.recipe.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.adapter.in.dto.InRecipeCourseDto;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDto;
import refrigerator.back.recipe.adapter.in.dto.InRecipeIngredientDto;
import refrigerator.back.recipe.adapter.out.dto.OutRecipeDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeBasicDataMapper {
    RecipeBasicDataMapper INSTANCE = Mappers.getMapper(RecipeBasicDataMapper.class);

    @Mappings({
            @Mapping(source = "dto.cookingTime", target = "cookingTime", qualifiedByName = "addCookingTimeUnit"),
            @Mapping(source = "dto.kcal", target = "kcal", qualifiedByName = "addKcalUnit"),
            @Mapping(source = "dto.servings", target = "servings", qualifiedByName = "addServingsUnit")
    })
    InRecipeDto toInRecipeDto(OutRecipeDto dto,
                              List<InRecipeIngredientDto> ingredients,
                              List<InRecipeCourseDto> courses,
                              Boolean isBookmarked);

    @Named("addKcalUnit")
    static String addKcalUnit(Integer kcal){
        return kcal + "kcal";
    }

    @Named("addServingsUnit")
    static String addServingsUnit(Integer servings){
        return servings + "인분";
    }

    @Named("addCookingTimeUnit")
    static String addCookingTimeUnit(Integer cookingTime){
        return cookingTime + "분";
    }
}
