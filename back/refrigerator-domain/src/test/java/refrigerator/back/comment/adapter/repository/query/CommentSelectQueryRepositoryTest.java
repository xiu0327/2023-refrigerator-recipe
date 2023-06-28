package refrigerator.back.comment.adapter.repository.query;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.comment.adapter.dto.OutCommentDTO;
import refrigerator.back.global.config.QuerydslConfig;

@DataJpaTest
@Import({QuerydslConfig.class, CommentSelectQueryRepository.class})
@TestDataInit({"/comment.sql", "/member.sql"})
@Slf4j
class CommentSelectQueryRepositoryTest {

    @Autowired CommentSelectQueryRepository queryRepository;

    @Test
    @DisplayName("댓글 미리 보기 조회 성공 테스트")
    void selectPreviewComments() {
        Page<OutCommentDTO> result = queryRepository.selectPreviewComments(1L, PageRequest.of(0, 3));
//        Assertions.assertEquals();
    }

    @Test
    void selectComments() {
    }

    @Test
    void selectMyComments() {
    }

    @Test
    void selectCommentHeartPeopleByCommendIds() {
    }
}