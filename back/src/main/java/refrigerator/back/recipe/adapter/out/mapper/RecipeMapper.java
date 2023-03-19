package refrigerator.back.recipe.adapter.out.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.adapter.out.dto.RecipeListMappingDTO;
import refrigerator.back.recipe.adapter.out.dto.RecipeMappingDTO;
import refrigerator.back.recipe.adapter.out.entity.RecipeCourse;
import refrigerator.back.recipe.adapter.out.entity.RecipeIngredient;
import refrigerator.back.recipe.application.domain.entity.RecipeCourseDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeIngredientDomain;
import refrigerator.back.recipe.application.domain.value.RecipeDifficulty;
import refrigerator.back.recipe.application.domain.value.RecipeIngredientType;
import refrigerator.back.recipe.application.domain.value.RecipeType;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecipeMapper {
    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    @Mappings({
            @Mapping(source = "difficulty", target = "difficulty", qualifiedByName = "typeNameToEnum", defaultValue = "NO_LEVEL"),
            @Mapping(source = "recipeFoodTypeName", target = "recipeFoodType", defaultValue = "타입 없음"),
            @Mapping(source = "recipeCategoryName", target = "recipeCategory", defaultValue = "카테고리 없음"),
            @Mapping(source = "recipeType", target = "recipeType", defaultValue = "NOT_TYPE", qualifiedByName = "recipeTypeNameToType")
    })
    RecipeDomain toRecipeDomain(RecipeMappingDTO recipe);

    RecipeDomain listDtoToRecipeDomain(RecipeListMappingDTO recipe);

    RecipeCourseDomain toCourseDomain(RecipeCourse entity);

    @Mapping(source = "type", target = "type", qualifiedByName = "ingredientTypeNameToType", defaultValue = "NOT_TYPE")
    RecipeIngredientDomain toIngredientDomain(RecipeIngredient ingredient);

    /* 커스텀 메서드 */

    @Named("ingredientTypeNameToType")
    default RecipeIngredientType toIngredientType(String name){
        return RecipeIngredientType.lookup(name);
    }

    @Named("typeNameToEnum")
    default RecipeDifficulty toRecipeDifficulty(String name){
        return RecipeDifficulty.lookupByLevelName(name);
    }

    @Named("enumToTypeName")
    default String toRecipeDifficultyName(RecipeDifficulty type){
        return type.getLevelName();
    }

    @Named("recipeTypeNameToType")
    default RecipeType toRecipeTypeNameToType(String name){
        return RecipeType.lookup(name);
    }
}
