package refrigerator.back.comment.application.port.out;

import java.util.List;

public interface CommentHeartPeopleReadPort {
    List<Long> findLikedComment(String memberId);
}
