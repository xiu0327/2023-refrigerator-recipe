package refrigerator.server.api.recipe_search.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import refrigerator.back.recipe_search.application.domain.RecipeSearchCondition;
import refrigerator.server.api.recipe_search.dto.InRecipeSearchConditionDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-30T13:01:47+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.15 (JetBrains s.r.o.)"
)
@Component
public class InRecipeSearchMapperImpl implements InRecipeSearchMapper {

    @Override
    public RecipeSearchCondition toRecipeSearchCondition(InRecipeSearchConditionDto dto, String searchWord) {
        if ( dto == null && searchWord == null ) {
            return null;
        }

        RecipeSearchCondition.RecipeSearchConditionBuilder recipeSearchCondition = RecipeSearchCondition.builder();

        if ( dto != null ) {
            recipeSearchCondition.recipeType( dto.getRecipeType() );
            recipeSearchCondition.recipeFoodType( dto.getRecipeFoodType() );
            recipeSearchCondition.difficulty( dto.getDifficulty() );
            recipeSearchCondition.category( dto.getCategory() );
            recipeSearchCondition.score( dto.getScore() );
        }
        recipeSearchCondition.searchWord( searchWord );

        return recipeSearchCondition.build();
    }
}
