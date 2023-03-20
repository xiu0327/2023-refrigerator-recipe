package refrigerator.back.myscore.adapter.out.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import refrigerator.back.myscore.adapter.out.dto.MyRecipeScoreMappingDTO;
import refrigerator.back.myscore.adapter.out.dto.MyRecipeScorePreviewMappingDTO;
import refrigerator.back.myscore.adapter.out.dto.QMyRecipeScoreMappingDTO;
import refrigerator.back.myscore.adapter.out.dto.QMyRecipeScorePreviewMappingDTO;

import java.util.List;

import static refrigerator.back.myscore.adapter.out.entity.QMyRecipeScore.*;
import static refrigerator.back.recipe.adapter.out.entity.QRecipe.*;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeViews.*;

@Repository
@RequiredArgsConstructor
public class MyRecipeScoreQueryRepositoryImpl implements MyRecipeScoreQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public MyRecipeScoreMappingDTO findMyRecipeScoreById(Long scoreID) {
        return jpaQueryFactory
                .select(new QMyRecipeScoreMappingDTO(
                        myRecipeScore.scoreID,
                        recipe.recipeID,
                        recipe.recipeName,
                        recipe.image,
                        myRecipeScore.score,
                        recipeViews.views,
                        myRecipeScore.createDate))
                .from(myRecipeScore)
                .innerJoin(myRecipeScore).on(myRecipeScore.scoreID.eq(scoreID))
                .innerJoin(recipe).on(myRecipeScore.recipeID.eq(recipe.recipeID))
                .innerJoin(recipeViews).on(recipeViews.recipeID.eq(recipe.recipeID))
                .fetchOne();
    }

    @Override
    public List<MyRecipeScoreMappingDTO> findMyRecipeScoreList(String memberID, Pageable pageable) {
        return jpaQueryFactory
                .select(new QMyRecipeScoreMappingDTO(
                        myRecipeScore.scoreID,
                        recipe.recipeID,
                        recipe.recipeName,
                        recipe.image,
                        myRecipeScore.score,
                        recipeViews.views,
                        myRecipeScore.createDate))
                .from(myRecipeScore)
                .innerJoin(myRecipeScore).on(myRecipeScore.memberID.eq(memberID))
                .innerJoin(recipe).on(myRecipeScore.recipeID.eq(recipe.recipeID))
                .innerJoin(recipeViews).on(recipeViews.recipeID.eq(recipe.recipeID))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(myRecipeScore.createDate.desc())
                .fetch();
    }

    @Override
    public List<MyRecipeScorePreviewMappingDTO> findScorePreview(String memberID) {
        return jpaQueryFactory
                .select(new QMyRecipeScorePreviewMappingDTO(
                        myRecipeScore.scoreID,
                        recipe.recipeID,
                        recipe.recipeName,
                        recipe.image))
                .from(myRecipeScore)
                .innerJoin(myRecipeScore).on(myRecipeScore.memberID.eq(memberID))
                .innerJoin(recipe).on(myRecipeScore.recipeID.eq(recipe.recipeID))
                .orderBy(myRecipeScore.createDate.desc())
                .fetch();
    }

    @Override
    public Integer findMyRecipeScoreCount(String memberID) {
        return Math.toIntExact(jpaQueryFactory.select(myRecipeScore.count())
                .from(myRecipeScore)
                .where(myRecipeScore.memberID.eq(memberID))
                .fetchFirst());
    }
}
