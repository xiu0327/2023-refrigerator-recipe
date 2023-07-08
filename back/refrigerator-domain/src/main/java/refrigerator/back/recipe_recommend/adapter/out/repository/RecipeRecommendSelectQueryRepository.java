package refrigerator.back.recipe_recommend.adapter.out.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe_recommend.adapter.out.dto.OutRecipeIngredientNameDTO;
import refrigerator.back.recipe_recommend.adapter.out.dto.OutRecipeRecommendDTO;
import refrigerator.back.recipe_recommend.adapter.out.dto.QOutRecipeIngredientNameDTO;
import refrigerator.back.recipe_recommend.adapter.out.dto.QOutRecipeRecommendDTO;

import java.time.LocalDate;
import java.util.*;

import static refrigerator.back.ingredient.application.domain.QIngredient.ingredient;
import static refrigerator.back.recipe.application.domain.entity.QRecipe.recipe;
import static refrigerator.back.recipe.application.domain.entity.QRecipeIngredient.recipeIngredient;
import static refrigerator.back.recipe.application.domain.entity.QRecipeScore.recipeScore;

@Component
@RequiredArgsConstructor
public class RecipeRecommendSelectQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<OutRecipeIngredientNameDTO> selectRecipeIngredientNames() {
        return jpaQueryFactory.select(
                        new QOutRecipeIngredientNameDTO(
                                recipeIngredient.recipeID,
                                recipeIngredient.name,
                                recipeIngredient.type))
                .from(recipeIngredient)
                .fetch();
    }

    public Set<String> selectMyIngredientNames(LocalDate startDate, String memberId) {
        LocalDate endDate = LocalDate.of(2200, 1, 1);
        List<String> data = jpaQueryFactory.select(ingredient.name)
                .from(ingredient)
                .where(ingredient.email.eq(memberId),
                        ingredient.expirationDate.between(startDate, endDate))
                .fetch();
        return new HashSet<>(data);
    }

    public List<OutRecipeRecommendDTO> selectRecipeInfoByIds(List<Long> ids) {
        return jpaQueryFactory.select(new QOutRecipeRecommendDTO(
                        recipe.recipeID,
                        recipe.recipeName,
                        recipe.image,
                        recipeScore.score))
                .from(recipe)
                .innerJoin(recipeScore).on(recipeScore.recipeID.eq(recipe.recipeID))
                .where(recipe.recipeID.in(ids))
                .fetch();
    }
}
