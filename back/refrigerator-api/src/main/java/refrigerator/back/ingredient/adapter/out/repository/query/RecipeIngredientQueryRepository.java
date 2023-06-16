package refrigerator.back.ingredient.adapter.out.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.ingredient.adapter.out.dto.OutRecipeIngredientDTO;
import refrigerator.back.ingredient.adapter.out.dto.QOutRecipeIngredientDTO;

import java.util.List;

import static refrigerator.back.recipe.application.domain.entity.QRecipeIngredient.recipeIngredient;

@Repository
@RequiredArgsConstructor
public class RecipeIngredientQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<OutRecipeIngredientDTO> getRecipeIngredientList(Long recipeId) {
        return jpaQueryFactory
                .select(new QOutRecipeIngredientDTO(recipeIngredient.ingredientID, recipeIngredient.name))
                .from(recipeIngredient)
                .where(recipeIngredient.recipeID.eq(recipeId))
                .fetch();
    }

}
