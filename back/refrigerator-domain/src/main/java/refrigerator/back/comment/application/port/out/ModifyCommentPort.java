package refrigerator.back.comment.application.port.out;

import java.time.LocalDateTime;

public interface ModifyCommentPort {
    Long modifyContent(Long id, String content, LocalDateTime now);
    void modifyHeartCount(Long id, int value);
}
