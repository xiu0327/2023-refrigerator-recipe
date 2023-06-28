package refrigerator.back.comment.application.port.out.trash;

import java.util.List;

public interface FindCommentHeartPeoplePort {
    List<Long> findLikedComment(String memberId);
}
