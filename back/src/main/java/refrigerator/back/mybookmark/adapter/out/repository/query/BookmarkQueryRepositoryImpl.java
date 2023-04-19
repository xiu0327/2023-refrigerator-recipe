package refrigerator.back.mybookmark.adapter.out.repository.query;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import refrigerator.back.mybookmark.adapter.out.dto.OutBookmarkDTO;
import refrigerator.back.mybookmark.adapter.out.dto.OutBookmarkPreviewDTO;
import refrigerator.back.mybookmark.adapter.out.dto.QOutBookmarkDTO;
import refrigerator.back.mybookmark.adapter.out.dto.QOutBookmarkPreviewDTO;


import java.util.List;

import static refrigerator.back.mybookmark.application.domain.QMyBookmark.*;
import static refrigerator.back.recipe.application.domain.entity.QRecipe.recipe;
import static refrigerator.back.recipe.application.domain.entity.QRecipeScore.recipeScore;
import static refrigerator.back.recipe.application.domain.entity.QRecipeViews.recipeViews;


@Repository
@RequiredArgsConstructor
public class BookmarkQueryRepositoryImpl implements BookmarkQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<OutBookmarkPreviewDTO> findBookmarkPreview(String memberId, Pageable pageable) {
        List<OutBookmarkPreviewDTO> content = jpaQueryFactory
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory.select(myBookmark.count())
                .from(myBookmark)
                .where(myBookmark.memberId.eq(memberId),
                        myBookmark.deleted.eq(false));
        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
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

    @Override
    public List<Long> findRecipeIdAddedBookmarks(String memberId) {
        return jpaQueryFactory.select(myBookmark.recipeId)
                .from(myBookmark)
                .where(myBookmark.memberId.eq(memberId),
                        myBookmark.deleted.eq(false))
                .fetch();
    }
}
