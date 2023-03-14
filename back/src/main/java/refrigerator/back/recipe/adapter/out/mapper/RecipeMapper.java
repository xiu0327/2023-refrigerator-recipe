package refrigerator.back.recipe.adapter.out.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.adapter.out.entity.Recipe;
import refrigerator.back.recipe.adapter.out.entity.RecipeCourse;
import refrigerator.back.recipe.adapter.out.entity.RecipeIngredient;
import refrigerator.back.recipe.application.domain.entity.RecipeCourseDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeIngredientDomain;
import refrigerator.back.recipe.application.domain.value.RecipeDifficulty;
import refrigerator.back.recipe.application.domain.value.RecipeIngredientType;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RecipeMapper {
    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    @Mapping(source = "difficulty", target = "difficulty", qualifiedByName = "typeNameToEnum", defaultValue = "NO_LEVEL")
    RecipeDomain toRecipeDomain(Recipe recipe);

    @Mappings({
            @Mapping(source = "ingredients", target = "ingredients", ignore = true),
            @Mapping(source = "difficulty", target = "difficulty", qualifiedByName = "enumToTypeName", defaultValue = "없음")
    })
    Recipe toRecipeEntity(RecipeDomain recipeDomain);

    RecipeCourseDomain toCourseDomain(RecipeCourse entity);

    @Mapping(source = "type", target = "type", qualifiedByName = "ingredientTypeNameToType", defaultValue = "NOT_TYPE")
    RecipeIngredientDomain toIngredientDomain(RecipeIngredient ingredient);

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
}
