package refrigerator.back.comment.adapter.in.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InCommentListDTO {
    private List<InCommentDTO> comments;
    private Integer count;
}
