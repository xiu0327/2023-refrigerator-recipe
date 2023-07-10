package refrigerator.back.mybookmark.outbound.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.mybookmark.application.domain.MyBookmark;
import refrigerator.back.mybookmark.outbound.dto.OutBookmarkPreviewDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestDataInit("mybookmark.sql")
@Import({QuerydslConfig.class, MyBookmarkSelectQueryRepository.class})
class MyBookmarkSelectQueryRepositoryTest {

    @Autowired MyBookmarkSelectQueryRepository queryRepository;

    @Test
    @DisplayName("북마크 미리보기 목록 조회")
    void selectMyBookmarkPreviews() {
        // given
        String memberId = "jktest101@gmail.com";
        LocalDateTime preDate = LocalDateTime.of(2023, 7, 2, 1, 1);
        int size = 5;
        // when
        List<OutBookmarkPreviewDto> result = queryRepository.selectMyBookmarkPreviews(memberId, PageRequest.of(0, size));
        // then
        assertEquals(size, result.size());
        /* 최신순으로 정렬이 되었는지 확인 */
        for (OutBookmarkPreviewDto dto : result) {
            assertTrue(preDate.compareTo(dto.getCreateDateTime()) >= 0);
        }
    }

    @Test
    @DisplayName("")
    void selectNumberOfMyBookmarks() {
    }

    @Test
    void selectMyBookmarks() {
    }

}