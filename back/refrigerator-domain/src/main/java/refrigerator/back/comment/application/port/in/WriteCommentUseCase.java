package refrigerator.back.comment.application.port.in;

import java.time.LocalDateTime;

public interface WriteCommentUseCase {
    Long write(Long recipeId, String memberId, String content, LocalDateTime writeDate);
}
