package refrigerator.back.mybookmark.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkDTO;
import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkPreviewDTO;
import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkPreviewListDTO;
import refrigerator.back.mybookmark.application.domain.MyBookmark;
import refrigerator.back.recipe.application.domain.entity.RecipeBookmark;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class MyBookmarkServiceTest {

    @Autowired MyBookmarkService service;
    @Autowired TestData testData;
    @Autowired EntityManager em;

    @Test
    void 북마크_추가() {
        // given
        String memberId = createMember();
        Long recipeId = 1L;
        int before = em.find(RecipeBookmark.class, recipeId).getCount();
        // when
        Long bookmarkId = service.add(memberId, recipeId);
        // then
        MyBookmark myBookmark = em.find(MyBookmark.class, bookmarkId);
        int after = em.find(RecipeBookmark.class, recipeId).getCount();
        assertThat(myBookmark).isNotNull();
        assertThat(myBookmark.getDeleted()).isFalse();
        assertThat(myBookmark.getCreateDate()).isNotNull();
        assertThat(myBookmark.getMemberId()).isEqualTo(memberId);
        /* 북마크를 추가하면 레시피 북마크 개수가 증가 */
        assertThat(after).isEqualTo(before + 1);
    }

    @Test
    void 북마크_조회() {
        // given
        String memberId = createMember();
        for (long recipeID = 1L; recipeID < 15L ; recipeID++){
            service.add(memberId, recipeID);
        }
        // when
        List<InBookmarkDTO> bookmarks = service.findBookmarks(memberId, 0, 11).getBookmarks();
        // then
        assertThat(bookmarks).isNotEmpty();
        assertThat(bookmarks.size()).isEqualTo(11);
        for (InBookmarkDTO bookmark : bookmarks) {
            log.info("bookmark = {}", bookmark);
            assertNotNull(bookmark.getBookmarkId());
            assertNotNull(bookmark.getRecipeId());
            assertNotNull(bookmark.getRecipeImage());
            assertNotNull(bookmark.getRecipeName());
            assertNotNull(bookmark.getViews());
            assertNotNull(bookmark.getScoreAvg());
        }
    }

    @Test
    void 북마크_미리보기() {
        // given
        String memberId = createMember();
        for (long recipeID = 1L; recipeID < 15L ; recipeID++){
            service.add(memberId, recipeID);
        }
        // when
        InBookmarkPreviewListDTO previews = service.findPreviews(memberId, 5);
        // then
        List<InBookmarkPreviewDTO> bookmarks = previews.getBookmarks();
        Integer count = previews.getCount();
        assertThat(bookmarks).isNotEmpty();
        assertThat(count >= 5).isTrue();
        for (InBookmarkPreviewDTO bookmark : bookmarks) {
            log.info("bookmark = {}", bookmark);
            assertNotNull(bookmark.getBookmarkId());
            assertNotNull(bookmark.getRecipeId());
            assertNotNull(bookmark.getRecipeImage());
            assertNotNull(bookmark.getRecipeName());
        }

    }

    @Test
    void 북마크_삭제() {
        // given
        String memberId = createMember();
        Long deletedRecipeId = 3L;
        Long deletedId = null;
        for (long recipeID = 1L; recipeID < 5L ; recipeID++){
            Long bookmarkId = service.add(memberId, recipeID);
            if (recipeID == deletedRecipeId){
                deletedId = bookmarkId;
            }
        }
        List<InBookmarkPreviewDTO> before = service.findPreviews(memberId, 10).getBookmarks();
        int beforeCount = em.find(RecipeBookmark.class, deletedRecipeId).getCount();
        // when
        service.remove(deletedId);
        // then
        MyBookmark deletedBookmark = em.find(MyBookmark.class, deletedId);
        int afterCount = em.find(RecipeBookmark.class, deletedRecipeId).getCount();
        assertThat(deletedBookmark.getDeleted()).isTrue();
        List<InBookmarkPreviewDTO> after = service.findPreviews(memberId, 10).getBookmarks();
        assertThat(deletedBookmark.getBookmarkId()).isIn(getBookmarkIdList(before));
        assertThat(deletedBookmark.getBookmarkId()).isNotIn(getBookmarkIdList(after));
        /* 북마크를 삭제하면, 레시피의 북마크 개수가 1 감소 */
        assertThat(afterCount).isEqualTo(beforeCount - 1);
    }

    private List<Long> getBookmarkIdList(List<InBookmarkPreviewDTO> list) {
        return list.stream()
                .map(InBookmarkPreviewDTO::getBookmarkId)
                .collect(Collectors.toList());
    }

    private String createMember() {
        testData.createMember();
        return TestData.MEMBER_EMAIL;
    }
}