package refrigerator.back.comment.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.comment.application.domain.CommentHeartPeople;
import refrigerator.back.comment.application.domain.CommentHeartValue;
import refrigerator.back.comment.application.port.out.ChangeCommentHeartCountPort;
import refrigerator.back.comment.application.port.out.FindCommentHeartPeoplePort;
import refrigerator.back.comment.application.port.out.RemoveCommentHeartPeoplePort;
import refrigerator.back.comment.application.port.out.SaveCommentHeartPeoplePort;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.global.exception.BusinessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CommentHeartChangeServiceMockTest {

    @InjectMocks CommentHeartChangeService service;
    @Mock ChangeCommentHeartCountPort changeCommentHeartCountPort;
    @Mock FindCommentHeartPeoplePort findCommentHeartPeoplePort;

    /* 테스트 코드 내에선 사용하지 않지만 빈 등록을 위해 필요한 Mock */
    @Mock SaveCommentHeartPeoplePort saveCommentHeartPeoplePort;
    @Mock RemoveCommentHeartPeoplePort removeCommentHeartPeoplePort;

    @Test
    @DisplayName("하트수 증가 성공 테스트")
    void addHeartSuccessTest() {
        // given
        Long commentId = 1L;
        String memberId = "email";
        BDDMockito.given(findCommentHeartPeoplePort.findPeopleOne(commentId, memberId))
                .willReturn(Optional.empty());
        BDDMockito.given(changeCommentHeartCountPort.change(commentId, CommentHeartValue.ADD))
                .willReturn(1L);
        // when
        assertDoesNotThrow(() ->  service.addHeart(commentId, memberId));
    }

    @Test
    @DisplayName("하트수 증가 실패 테스트")
    void addHeartFailTest() {
        // given
        String id = "id";
        Long commentId = 1L;
        String memberId = "email";
        BDDMockito.given(findCommentHeartPeoplePort.findPeopleOne(commentId, memberId))
                .willReturn(Optional.of(new CommentHeartPeople(id, commentId, memberId)));
        // when
        assertThrows(BusinessException.class, () -> {
            try{
                service.addHeart(commentId, memberId);
            } catch (BusinessException e){
                assertEquals(e.getBasicExceptionType(), CommentExceptionType.DUPLICATE_HEART_REQUEST);
                throw e;
            }
        });
    }

    @Test
    @DisplayName("하트수 감소 성공 테스트")
    void reduceHeartSuccessTest() {
        // given
        Long commentId = 1L;
        String memberId = "email";
        String id = "id";
        CommentHeartPeople people = new CommentHeartPeople(id, commentId, memberId);
        BDDMockito.given(findCommentHeartPeoplePort.findPeopleOneById(people.getId()))
                .willReturn(Optional.of(people));
        BDDMockito.given(changeCommentHeartCountPort.change(commentId, CommentHeartValue.REDUCE))
                .willReturn(1L);
        // when
        assertDoesNotThrow(() -> service.reduceHeart(commentId, people.getId()));
    }

    @Test
    @DisplayName("하트수 감소 실패 테스트")
    void reduceHeartFailTest() {
        // given
        Long commentId = 1L;
        String peopleId = "peopleId";
        BDDMockito.given(findCommentHeartPeoplePort.findPeopleOneById(peopleId))
                .willReturn(Optional.empty());
        // when
        assertThrows(BusinessException.class, () -> {
            try{
                service.reduceHeart(commentId, peopleId);
            } catch (BusinessException e){
                assertEquals(e.getBasicExceptionType(), CommentExceptionType.DUPLICATE_HEART_REQUEST);
                throw e;
            }
        });
    }
}