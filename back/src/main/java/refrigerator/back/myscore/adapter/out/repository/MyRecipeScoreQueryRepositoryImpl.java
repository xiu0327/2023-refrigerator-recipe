package refrigerator.back.myscore.adapter.out.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import refrigerator.back.myscore.adapter.out.dto.MyRecipeScoreMappingDTO;
import refrigerator.back.myscore.adapter.out.dto.QMyRecipeScoreMappingDTO;

import java.util.List;

import static refrigerator.back.myscore.adapter.out.entity.QMyRecipeScore.*;
import static refrigerator.back.recipe.adapter.out.entity.QRecipe.*;

@Repository
@RequiredArgsConstructor
public class MyRecipeScoreQueryRepositoryImpl implements MyRecipeScoreQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public MyRecipeScoreMappingDTO findMyRecipeScoreById(Long scoreID) {
        return jpaQueryFactory
                .select(new QMyRecipeScoreMappingDTO(
                        myRecipeScore.scoreID,
                        recipe.recipeName,
                        recipe.image,
                        myRecipeScore.score))
                .from(myRecipeScore)
                .innerJoin(myRecipeScore).on(myRecipeScore.scoreID.eq(scoreID))
                .innerJoin(recipe).on(myRecipeScore.recipeID.eq(recipe.recipeID))
                .fetchOne();
    }

    @Override
    public List<MyRecipeScoreMappingDTO> findMyRecipeScoreList(String memberID, Pageable pageable) {
        return jpaQueryFactory
                .select(new QMyRecipeScoreMappingDTO(
                        myRecipeScore.scoreID,
                        recipe.recipeName,
                        recipe.image,
                        myRecipeScore.score))
                .from(myRecipeScore)
                .innerJoin(myRecipeScore).on(myRecipeScore.memberID.eq(memberID))
                .innerJoin(recipe).on(myRecipeScore.recipeID.eq(recipe.recipeID))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
