package refrigerator.back.comment.adapter.in.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InCommentListDTO {
    private List<InCommentDTO> comments;
    private Integer count;
}
