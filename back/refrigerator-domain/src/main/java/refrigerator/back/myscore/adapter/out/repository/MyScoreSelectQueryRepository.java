package refrigerator.back.myscore.adapter.out.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import refrigerator.back.myscore.adapter.out.dto.OutMyScoreDTO;
import refrigerator.back.myscore.adapter.out.dto.OutMyScorePreviewDTO;
import refrigerator.back.myscore.adapter.out.dto.QOutMyScoreDTO;
import refrigerator.back.myscore.adapter.out.dto.QOutMyScorePreviewDTO;

import java.util.List;

import static refrigerator.back.myscore.application.domain.QMyScore.myScore;

import static refrigerator.back.recipe.application.domain.entity.QRecipe.recipe;
import static refrigerator.back.recipe.application.domain.entity.QRecipeScore.recipeScore;
import static refrigerator.back.recipe.application.domain.entity.QRecipeViews.recipeViews;


@Component
@RequiredArgsConstructor
public class MyScoreSelectQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<OutMyScoreDTO> selectMyRecipeScoreList(String memberID, Pageable pageable) {

        return jpaQueryFactory
                .select(new QOutMyScoreDTO(
                        myScore.scoreID,
                        recipe.recipeID,
                        recipe.image,
                        recipe.recipeName,
                        recipeScore.score,
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

    public Page<OutMyScorePreviewDTO> selectScorePreview(String memberID, Pageable page) {
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
