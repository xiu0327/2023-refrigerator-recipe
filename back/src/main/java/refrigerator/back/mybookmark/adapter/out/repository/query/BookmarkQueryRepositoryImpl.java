package refrigerator.back.mybookmark.adapter.out.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import refrigerator.back.mybookmark.adapter.out.dto.OutBookmarkDTO;
import refrigerator.back.mybookmark.adapter.out.dto.OutBookmarkPreviewDTO;
import refrigerator.back.mybookmark.adapter.out.dto.QOutBookmarkDTO;
import refrigerator.back.mybookmark.adapter.out.dto.QOutBookmarkPreviewDTO;


import java.util.List;

import static refrigerator.back.mybookmark.application.domain.QMyBookmark.*;
import static refrigerator.back.recipe.adapter.out.entity.QRecipe.*;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeScore.*;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeViews.*;


@Repository
@RequiredArgsConstructor
public class BookmarkQueryRepositoryImpl implements BookmarkQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OutBookmarkPreviewDTO> findBookmarkPreview(String memberId) {
        return jpaQueryFactory
                .select(new QOutBookmarkPreviewDTO(
                        myBookmark.bookmarkId,
                        myBookmark.recipeId,
                        recipe.image,
                        recipe.recipeName))
                .from(myBookmark)
                .leftJoin(recipe).on(recipe.recipeID.eq(myBookmark.recipeId))
                .where(myBookmark.memberId.eq(memberId),
                        myBookmark.deleted.eq(false))
                .orderBy(myBookmark.createDate.desc())
                .fetch();
    }

    @Override
    public List<OutBookmarkDTO> findBookmarkList(String memberId, Pageable pageable) {
        return jpaQueryFactory
                .select(new QOutBookmarkDTO(
                        myBookmark.bookmarkId,
                        myBookmark.recipeId,
                        recipe.image,
                        recipe.recipeName,
                        recipeScore,
                        recipeViews.views))
                .from(myBookmark)
                .leftJoin(recipe).on(recipe.recipeID.eq(myBookmark.recipeId))
                .leftJoin(recipeScore).on(recipeScore.recipeID.eq(myBookmark.recipeId))
                .leftJoin(recipeViews).on(recipeViews.recipeID.eq(myBookmark.recipeId))
                .where(myBookmark.memberId.eq(memberId),
                        myBookmark.deleted.eq(false))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(myBookmark.createDate.desc())
                .fetch();
    }
}
