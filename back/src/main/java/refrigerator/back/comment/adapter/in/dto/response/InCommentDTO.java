package refrigerator.back.comment.adapter.in.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class InCommentDTO {
    private Long commentID;
    private String nickname;
    private Integer heart;
    private String date;
    private Boolean modifiedState;
    private String content;
}
