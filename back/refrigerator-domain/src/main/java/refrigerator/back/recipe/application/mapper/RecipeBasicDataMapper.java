package refrigerator.back.recipe.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.application.dto.RecipeCourseDto;
import refrigerator.back.recipe.application.dto.RecipeDomainDto;
import refrigerator.back.recipe.application.dto.RecipeDto;
import refrigerator.back.recipe.application.dto.RecipeIngredientDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeBasicDataMapper {
    RecipeBasicDataMapper INSTANCE = Mappers.getMapper(RecipeBasicDataMapper.class);

    @Mappings({
            @Mapping(source = "dto.cookingTime", target = "cookingTime", qualifiedByName = "addCookingTimeUnit"),
            @Mapping(source = "dto.kcal", target = "kcal", qualifiedByName = "addKcalUnit"),
            @Mapping(source = "dto.servings", target = "servings", qualifiedByName = "addServingsUnit")
    })
    RecipeDto toInRecipeDto(RecipeDomainDto dto,
                            List<RecipeIngredientDto> ingredients,
                            List<RecipeCourseDto> courses,
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
