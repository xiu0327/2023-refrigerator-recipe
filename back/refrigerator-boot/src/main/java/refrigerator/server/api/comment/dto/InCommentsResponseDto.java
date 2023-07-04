package refrigerator.server.api.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.comment.application.dto.InCommentDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class InCommentsResponseDto {

    private List<InCommentDto> comments;
    private List<InCommentDto> myComments;

    public InCommentsResponseDto(List<InCommentDto> comments, List<InCommentDto> myComments) {
        this.comments = comments;
        this.myComments = myComments;
    }
}
