package refrigerator.back.myscore.adapter.out.repository.query;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import refrigerator.back.myscore.adapter.out.dto.*;
import refrigerator.back.recipe.application.domain.entity.QRecipe;
import refrigerator.back.recipe.application.domain.entity.QRecipeScore;

import java.util.List;

import static refrigerator.back.myscore.application.domain.QMyScore.*;
import static refrigerator.back.recipe.application.domain.entity.QRecipe.recipe;
import static refrigerator.back.recipe.application.domain.entity.QRecipeScore.*;
import static refrigerator.back.recipe.application.domain.entity.QRecipeViews.recipeViews;

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
                        recipe.image,
                        recipe.recipeName,
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
    public Page<OutMyScorePreviewDTO> findScorePreview(String memberID, Pageable page) {
        List<OutMyScorePreviewDTO> content = jpaQueryFactory
                .select(new QOutMyScorePreviewDTO(
                        myScore.scoreID,
                        recipe.recipeID,
                        recipe.image,
                        recipe.recipeName))
                .from(myScore)
                .leftJoin(recipe).on(recipe.recipeID.eq(myScore.recipeID))
                .where(myScore.memberID.eq(memberID))
                .orderBy(myScore.createDate.desc())
                .limit(page.getPageSize())
                .offset(page.getOffset())
                .fetch();
        JPAQuery<Long> count = jpaQueryFactory.select(myScore.count())
                .from(myScore)
                .where(myScore.memberID.eq(memberID));
        return PageableExecutionUtils.getPage(content, page, count::fetchOne);
    }

}
