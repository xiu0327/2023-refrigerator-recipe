package refrigerator.back.notification.application.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentNotificationDetails {
    private String authorId;
    private Long recipeId;
}
