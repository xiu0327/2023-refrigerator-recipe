package refrigerator.server.api.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.comment.application.dto.InCommentDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class InCommentsPreviewResponseDto {

    private List<InCommentDto> comments;
    private Integer count;

    public InCommentsPreviewResponseDto(List<InCommentDto> comments, Integer count) {
        this.comments = comments;
        this.count = count;
    }
}
