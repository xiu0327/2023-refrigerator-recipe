package refrigerator.back.comment.adapter.in.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InCommentListDTO {
    private List<InCommentDTO> comments;
    private Integer count;
}
