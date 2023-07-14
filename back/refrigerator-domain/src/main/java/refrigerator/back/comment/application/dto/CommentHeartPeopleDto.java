package refrigerator.server.api.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
<<<<<<<< HEAD:back/refrigerator-domain/src/main/java/refrigerator/back/comment/application/dto/CommentHeartPeopleDto.java
public class CommentHeartPeopleDto {

    private String peopleId;
    private Long commentId;

========
public class InCommentIdResponseDto {
    private Long commentID;
>>>>>>>> origin/back-dh-v1:back/refrigerator-boot/src/main/java/refrigerator/server/api/comment/dto/InCommentIdResponseDto.java
}
