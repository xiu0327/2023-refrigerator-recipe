package refrigerator.back.recipe.adapter.out.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.out.dto.OutRecipeIngredientNameDTO;
import refrigerator.back.recipe.adapter.out.dto.OutRecipeRecommendDTO;
import refrigerator.back.recipe.adapter.out.dto.QOutRecipeIngredientNameDTO;
import refrigerator.back.recipe.adapter.out.dto.QOutRecipeRecommendDTO;
import refrigerator.back.recipe.application.domain.RecipeIngredientTuple;


import java.time.LocalDate;
import java.util.*;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.Projections.list;
import static refrigerator.back.ingredient.application.domain.QIngredient.ingredient;
import static refrigerator.back.recipe.application.domain.entity.QRecipe.recipe;
import static refrigerator.back.recipe.application.domain.entity.QRecipeIngredient.recipeIngredient;
import static refrigerator.back.recipe.application.domain.entity.QRecipeScore.recipeScore;

@Repository
@RequiredArgsConstructor
public class RecipeRecommendQueryRepositoryImpl implements RecipeRecommendQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> findIngredientName(String memberId) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.of(2200, 1, 1);
        return jpaQueryFactory.select(ingredient.name)
                .from(ingredient)
                .where(ingredient.email.eq(memberId),
                        ingredient.expirationDate.between(startDate, endDate))
                .fetch();
    }

    @Override
    public List<OutRecipeRecommendDTO> findRecommendRecipes(List<Long> recipeIds) {
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
    public Map<Long, Set<RecipeIngredientTuple>> findRecipeIngredientNames() {
        // 1. DB 에서 레시피 식재료 명 데이터 가져오기
        List<OutRecipeIngredientNameDTO> data = jpaQueryFactory.select(
                        new QOutRecipeIngredientNameDTO(
                                recipeIngredient.recipeID,
                                recipeIngredient.name,
                                recipeIngredient.type))
                .from(recipeIngredient)
                .fetch();
        // 2. 동일한 recipeId 별로 Map 자료구조 형성
        Map<Long, Set<RecipeIngredientTuple>> result = new HashMap<>();
        data.forEach(ingredient -> {
            Set<RecipeIngredientTuple> tuples = result.getOrDefault(
                    ingredient.getRecipeId(),
                    new HashSet<>());
            tuples.add(new RecipeIngredientTuple(
                    ingredient.getIngredientName(),
                    ingredient.getType()
            ));
            result.put(ingredient.getRecipeId(), tuples);
        });
        return result;
    }
}
