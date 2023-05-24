package refrigerator.back.notification.adapter.out;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.notification.adapter.mapper.NotificationMapper;
import refrigerator.back.notification.adapter.out.dto.OutCommentDetailsDTO;
import refrigerator.back.notification.adapter.out.dto.QOutCommentDetailsDTO;
import refrigerator.back.notification.application.domain.CommentNotificationDetails;
import refrigerator.back.notification.application.port.out.read.FindCommentDetailsPort;
import refrigerator.back.notification.application.port.out.read.FindSenderNicknamePort;

import static refrigerator.back.comment.application.domain.QComment.*;
import static refrigerator.back.member.application.domain.QMember.*;

@Repository
@RequiredArgsConstructor
public class NotificationInfoAdapter implements FindSenderNicknamePort, FindCommentDetailsPort {

    private final JPAQueryFactory jpaQueryFactory;
    private final NotificationMapper mapper;

    @Override
    public String getNickname(String memberId) {
        return jpaQueryFactory.select(member.nickname)
                .from(member)
                .where(member.email.eq(memberId))
                .fetchOne();
    }

    @Override
    public CommentNotificationDetails getDetails(Long commentId) {
        OutCommentDetailsDTO dto = jpaQueryFactory.select(new QOutCommentDetailsDTO(comment.memberID, comment.recipeID))
                .from(comment)
                .where(comment.commentID.eq(commentId))
                .fetchOne();
        return mapper.toCommentNotificationDetail(dto);
    }
}
