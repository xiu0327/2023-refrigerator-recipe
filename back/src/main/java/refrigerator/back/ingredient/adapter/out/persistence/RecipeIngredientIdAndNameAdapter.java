package refrigerator.back.ingredient.adapter.out.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.ingredient.adapter.mapper.RecipeIngredientMapper;
import refrigerator.back.ingredient.adapter.out.dto.QOutRecipeIngredientDTO;
import refrigerator.back.ingredient.application.domain.RecipeIngredientDto;
import refrigerator.back.ingredient.application.port.out.FindRecipeIngredientPort;

import java.util.List;
import java.util.stream.Collectors;

import static refrigerator.back.recipe.application.domain.entity.QRecipeIngredient.recipeIngredient;

@Repository
@RequiredArgsConstructor
public class RecipeIngredientIdAndNameAdapter implements FindRecipeIngredientPort {

    private final JPAQueryFactory jpaQueryFactory;
    private final RecipeIngredientMapper mapper;

    @Override
    public List<RecipeIngredientDto> getData(Long recipeId) {
        return jpaQueryFactory.select(new QOutRecipeIngredientDTO(recipeIngredient.ingredientID, recipeIngredient.name))
                .from(recipeIngredient)
                .where(recipeIngredient.recipeID.eq(recipeId))
                .fetch()
                .stream().map(mapper::toRecipeIngredientDto)
                .collect(Collectors.toList());
    }
}
