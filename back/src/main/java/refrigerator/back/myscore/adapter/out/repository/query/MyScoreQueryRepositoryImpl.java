package refrigerator.back.myscore.adapter.out.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import refrigerator.back.myscore.adapter.out.dto.*;

import java.util.List;

import static refrigerator.back.myscore.application.domain.QMyScore.*;
import static refrigerator.back.recipe.adapter.out.entity.QRecipe.*;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeScore.*;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeViews.*;

@Repository
@RequiredArgsConstructor
public class MyScoreQueryRepositoryImpl implements MyScoreQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OutMyScoreDTO> findMyRecipeScoreList(String memberID, Pageable pageable) {
        return jpaQueryFactory
                .select(new QOutMyScoreDTO(
                        myScore.scoreID,
                        recipe.recipeID,
                        recipe.recipeName,
                        recipe.image,
                        recipeScore,
                        recipeViews.views))
                .from(myScore)
                .leftJoin(recipe).on(recipe.recipeID.eq(myScore.recipeID))
                .leftJoin(recipeScore).on(recipeScore.recipeID.eq(myScore.recipeID))
                .leftJoin(recipeViews).on(recipeViews.recipeID.eq(myScore.recipeID))
                .where(myScore.memberID.eq(memberID))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(myScore.createDate.desc())
                .fetch();
    }

    @Override
    public List<OutMyScorePreviewDTO> findScorePreview(String memberID) {
        return jpaQueryFactory
                .select(new QOutMyScorePreviewDTO(
                        myScore.scoreID,
                        recipe.recipeID,
                        recipe.recipeName,
                        recipe.image))
                .from(myScore)
                .leftJoin(recipe).on(recipe.recipeID.eq(myScore.recipeID))
                .where(myScore.memberID.eq(memberID))
                .orderBy(myScore.createDate.desc())
                .fetch();
    }

}
