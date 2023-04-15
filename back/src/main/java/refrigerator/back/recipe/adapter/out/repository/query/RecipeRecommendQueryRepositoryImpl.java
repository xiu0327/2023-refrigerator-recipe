package refrigerator.back.recipe.adapter.out.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.out.dto.OutRecipeRecommendDTO;
import refrigerator.back.recipe.adapter.out.dto.OutRecipeRecommendIngredientDTO;
import refrigerator.back.recipe.adapter.out.dto.QOutRecipeRecommendDTO;
import refrigerator.back.recipe.adapter.out.dto.QOutRecipeRecommendIngredientDTO;

import java.util.List;

import static refrigerator.back.ingredient.application.domain.QIngredient.ingredient;
import static refrigerator.back.recipe.application.domain.entity.QRecipe.*;
import static refrigerator.back.recipe.application.domain.entity.QRecipeIngredient.recipeIngredient;
import static refrigerator.back.recipe.application.domain.entity.QRecipeScore.*;

@Repository
@RequiredArgsConstructor
public class RecipeRecommendQueryRepositoryImpl implements RecipeRecommendQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> findIngredientNameByMember(String memberId) {
        return jpaQueryFactory.select(ingredient.name)
                .from(ingredient)
                .where(ingredient.email.eq(memberId))
                .fetch();
    }

    @Override
    public List<OutRecipeRecommendDTO> findRecommendRecipeListById(List<Long> recipeIds) {
        return jpaQueryFactory.select(new QOutRecipeRecommendDTO(
                        recipe.recipeID,
                        recipe.recipeName,
                        recipe.image,
                        recipeScore))
                .from(recipe)
                .innerJoin(recipeScore).on(recipeScore.recipeID.eq(recipe.recipeID))
                .where(recipe.recipeID.in(recipeIds))
                .fetch();
    }

    @Override
    public List<OutRecipeRecommendIngredientDTO> findRecipeNameAndIngredientList() {
        return jpaQueryFactory.select(
                        new QOutRecipeRecommendIngredientDTO(
                                recipeIngredient.recipeID,
                                recipeIngredient.name))
                .from(recipeIngredient)
                .fetch();
    }
}
