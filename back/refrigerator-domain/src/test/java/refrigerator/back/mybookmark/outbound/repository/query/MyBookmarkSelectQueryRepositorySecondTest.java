package refrigerator.back.mybookmark.outbound.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.mybookmark.application.domain.MyBookmark;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import({QuerydslConfig.class, MyBookmarkSelectQueryRepository.class})
class MyBookmarkSelectQueryRepositorySecondTest {

    @Autowired MyBookmarkSelectQueryRepository queryRepository;
    @Autowired TestEntityManager em;

    @Test
    @DisplayName("북마크 id 조회 -> 중복 확인 위함")
    void selectBookmarkIdByRecipeIdAndMemberId() {
        // given
        String memberId = "memberId";
        Long recipeId = 1L;
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 10, 1, 1);
        MyBookmark myBookmark = new MyBookmark(memberId, recipeId, false, createDateTime);
        Long expectedId = em.persistAndFlush(myBookmark).getBookmarkId();
        // when
        Long result = queryRepository.selectBookmarkIdByRecipeIdAndMemberId(recipeId, memberId);
        // then
        assertEquals(expectedId, result);
    }
}