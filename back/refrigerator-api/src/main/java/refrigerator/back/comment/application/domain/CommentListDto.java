package refrigerator.back.comment.application.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CommentListDto {
    private List<CommentDto> comments;
    private Integer count;
}
