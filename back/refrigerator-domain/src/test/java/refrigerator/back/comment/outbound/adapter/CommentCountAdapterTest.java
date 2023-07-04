package refrigerator.back.comment.outbound.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.comment.application.port.out.FindNumberOfCommentsPort;
import refrigerator.back.comment.outbound.repository.query.CommentSelectQueryRepository;
import refrigerator.back.global.config.QuerydslConfig;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, CommentSelectQueryRepository.class})
@TestDataInit(value = {"/comment.sql"})
class CommentCountAdapterTest {

    @Autowired CommentSelectQueryRepository queryRepository;
    FindNumberOfCommentsPort findNumberOfCommentsPort;

    @BeforeEach
    void init(){
        findNumberOfCommentsPort = new CommentCountAdapter(queryRepository);
    }

    @Test
    @DisplayName("전체 댓글 개수 조회")
    void findNumberOfComments() {
        // given
        long recipeId = 1L;
        // when
        Long result = findNumberOfCommentsPort.getNumber(recipeId);
        // then
        assertEquals(11, result);
    }
}